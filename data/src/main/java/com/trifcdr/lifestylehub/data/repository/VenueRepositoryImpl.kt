package com.trifcdr.lifestylehub.data.repository

import com.example.lifestylehub.data.Resource
import com.trifcdr.lifestylehub.data.database.venueDB.DetailsDB
import com.trifcdr.lifestylehub.data.mappers.mapToDetailsModel
import com.trifcdr.lifestylehub.data.mappers.mapToDomainDetail
import com.trifcdr.lifestylehub.data.mappers.mapToDomainVenue
import com.trifcdr.lifestylehub.data.network.api.venue.FSVenueApi
import com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails.VenueDetailsResponse
import com.trifcdr.lifestylehub.data.network.models.venueModels.venueRecommends.VenueResponse
import com.trifcdr.lifestylehub.data.storage.AppStorage
import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.location.UserLocation
import com.trifcdr.lifestylehub.domain.models.venue.VenueDetailsDataDom
import com.trifcdr.lifestylehub.domain.models.venue.venueRecommends.VenueData
import com.trifcdr.lifestylehub.domain.repositories.VenueRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VenueRepositoryImpl(
    private val database: DetailsDB,
    private val venueApi: FSVenueApi,
    private val appStorage: AppStorage,
) : VenueRepository {


    override suspend fun getVenueByLocation(location: UserLocation, radius: Int, limit: Int, offset: Int): DomainResource<VenueData> {
        var response: Resource<VenueResponse> = Resource.Failure(Exception())
        if(checkAuth()){
            //first rty to get venue recommendations with old token
            response = venueApi.getVenueRecommendationsByLocation(
                location = location,
                radius = radius,
                limit = limit,
                offset = offset,
                oauthToken = appStorage.getKey()
            )
            //if the token has expired
            if (response is Resource.Forbidden){
                when (val token = venueApi.auth()){
                    is Resource.Success -> {
                        appStorage.saveKey(token.result.response.accessToken)
                    }
                    is Resource.Failure -> {
                        return DomainResource.Failure(token.exception)
                    }
                    else -> {
                        return DomainResource.Failure(Exception("auth error"))
                    }
                }
                //second try to get venue recommendations with new token
                response = venueApi.getVenueRecommendationsByLocation(
                    location = location,
                    radius = radius,
                    limit = limit,
                    offset = offset,
                    oauthToken = appStorage.getKey()
                )
            }
        }
        return mapToDomainVenue(response)
    }

    override suspend fun deleteDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteDetails()
        }
    }

    override suspend fun getVenueDetailsById(id: String): DomainResource<VenueDetailsDataDom> {
        var result: DomainResource<VenueDetailsDataDom> = DomainResource.Empty
        CoroutineScope(Dispatchers.IO).launch {
            val dbRes = database.getVenueDetailsById(id)
            if (dbRes == null){
                var response: Resource<VenueDetailsResponse>
                if(checkAuth()){
                    response = venueApi.getVenueDetails(appStorage.getKey(), id)
                    if (response is Resource.Forbidden){
                        when (val token = venueApi.auth()){
                            is Resource.Success -> {
                                appStorage.saveKey(token.result.response.accessToken)
                            }
                            is Resource.Failure -> {
                                result =  DomainResource.Failure(token.exception)
                                return@launch
                            }
                            else -> {
                                result =  DomainResource.Failure(Exception("auth error"))
                                return@launch
                            }
                        }
                        response = venueApi.getVenueDetails(appStorage.getKey(), id)
                    }
                    when (response){
                        is Resource.Success ->{
                            mapToDetailsModel(response)?.let {
                                database.insertVenueDetails(it)
                                result = DomainResource.Success(mapToDomainDetail(it))
                                return@launch
                            }
                        }
                        is Resource.Failure -> {
                            result = DomainResource.Failure(response.exception)
                            return@launch
                        }
                        else -> {
                            result = DomainResource.Failure(Exception("details error"))
                            return@launch
                        }
                    }
                }
            }
            result = DomainResource.Success(mapToDomainDetail(dbRes!!))
        }.join()
        return result
    }



    private suspend fun checkAuth(): Boolean{
        if (appStorage.getKey() != "empty"){
            return true
        }
        val token = venueApi.auth()
        if (token is Resource.Success) {
            appStorage.saveKey(token.result.response.accessToken)
            return true
        }
        return false

    }
}