package com.trifcdr.lifestylehub.data.network.api.venue

import com.example.lifestylehub.data.Resource
import com.example.lifestylehub.data.network.BASE_URL_FOURSQARE
import com.example.lifestylehub.data.network.KEY_VENUE
import com.trifcdr.lifestylehub.data.network.models.venueModels.venueRecommends.VenueResponse
import com.trifcdr.lifestylehub.data.network.models.venueModels.auth.AuthResponse
import com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails.VenueDetailsResponse
import com.trifcdr.lifestylehub.domain.models.location.UserLocation
import io.ktor.client.HttpClient
import io.ktor.client.features.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.HttpStatusCode

class FSVenueApiImpl(
    private val httpClient: HttpClient
) : FSVenueApi {

    override suspend fun auth(): Resource<AuthResponse> {
        return try{
            Resource.Success(
                httpClient.post<AuthResponse> {
                    url(AUTHORIZATION)
                    header("Authorization", "Bearer $KEY_VENUE")
                    parameter("v", API_VERSION)
                }
            )
        }
        catch (e: Exception){
            e as ResponseException
            if (e.response.status == HttpStatusCode.Unauthorized){
                return Resource.Unauthorized
            }
            e.printStackTrace()
            Resource.Failure(e)
        }

    }
    override suspend fun getVenueRecommendationsByLocation(
        oauthToken: String, location: UserLocation,
        radius: Int, limit: Int, offset: Int
    ): Resource<VenueResponse> {
        return try{
            Resource.Success(
                httpClient.get<VenueResponse>{
                    url(VENUE_RECOMMENDATIONS)
                    header("Accept-Language", "ru")
                    parameter("oauth_token", oauthToken)
                    parameter("v", API_VERSION)
                    parameter("radius", radius)
                    parameter("ll", "${location.lat},${location.lon}")
                    parameter("offset", offset)
                    parameter("limit", limit)
                }
            )
        }
        catch (e: Exception){
            if (e is ResponseException && e.response.status == HttpStatusCode.Unauthorized){
                return Resource.Unauthorized
            }
            if (e is ResponseException && e.response.status == HttpStatusCode.Forbidden){
                return Resource.Forbidden
            }
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getVenueDetails(
        oauthToken: String,
        venueId: String
    ): Resource<VenueDetailsResponse> {
        return try{
            Resource.Success(
                httpClient.get<VenueDetailsResponse>{
                    url("$VENUE_DETAILS/$venueId")
                    header("Accept-Language", "ru")
                    parameter("oauth_token", oauthToken)
                    parameter("v", API_VERSION)

                }
            )
        }
        catch (e: Exception){
            if (e is ResponseException && e.response.status == HttpStatusCode.Unauthorized){
                return Resource.Unauthorized
            }
            if (e is ResponseException && e.response.status == HttpStatusCode.Forbidden){
                return Resource.Forbidden
            }
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    companion object{
        private const val AUTHORIZATION = "$BASE_URL_FOURSQARE/usermanagement/createuser"
        private const val VENUE_RECOMMENDATIONS = "$BASE_URL_FOURSQARE/search/recommendations"
        private const val VENUE_DETAILS = "$BASE_URL_FOURSQARE/venues"
        private const val API_VERSION = 20231010
    }
}