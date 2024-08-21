package com.trifcdr.lifestylehub.data.mappers

import com.example.lifestylehub.data.Resource
import com.trifcdr.lifestylehub.data.database.leisureDB.LeisureModel
import com.trifcdr.lifestylehub.data.database.venueDB.VenueDetailsModel
import com.trifcdr.lifestylehub.data.network.models.venueModels.venueDetails.VenueDetailsResponse
import com.trifcdr.lifestylehub.data.network.models.venueModels.venueRecommends.VenueResponse
import com.trifcdr.lifestylehub.data.network.models.weatherModels.WeatherResponse
import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.leisure.Leisure
import com.trifcdr.lifestylehub.domain.models.user.User
import com.trifcdr.lifestylehub.domain.models.venue.VenueDetailsDataDom
import com.trifcdr.lifestylehub.domain.models.venue.venueRecommends.Category
import com.trifcdr.lifestylehub.domain.models.venue.venueRecommends.Photo
import com.trifcdr.lifestylehub.domain.models.venue.venueRecommends.Result
import com.trifcdr.lifestylehub.domain.models.venue.venueRecommends.Venue
import com.trifcdr.lifestylehub.domain.models.venue.venueRecommends.VenueData
import com.trifcdr.lifestylehub.domain.models.weather.Main
import com.trifcdr.lifestylehub.domain.models.weather.Weather
import com.trifcdr.lifestylehub.domain.models.weather.WeatherData
import org.json.JSONObject
import java.util.Collections

/**
 * Created by trifcdr.
 */

fun mapToDomainWeather(weatherResponseResult: Resource<WeatherResponse>): DomainResource<WeatherData> {
    when(weatherResponseResult){
        is Resource.Success -> {
            val res = weatherResponseResult.result
            val weatherData = mutableListOf<Weather>()
            for (el in res.weather){
                weatherData.add(
                    Weather(
                        description = el.description,
                        icon = el.icon,
                        id = el.id,
                        main = el.main
                    )
                )
            }
            return DomainResource.Success(
                WeatherData(
                    id = res.id,
                    main = Main(
                        feelsLike = res.main.feelsLike,
                        grndLevel = res.main.grndLevel,
                        humidity = 0,
                        pressure = res.main.pressure,
                        seaLevel = res.main.seaLevel,
                        temp = res.main.temp,
                        tempMax = res.main.tempMax,
                        tempMin = res.main.tempMin
                    ),
                    name = res.name,
                    weather = Collections.unmodifiableList(weatherData)

                )
            )
        }
        is Resource.Failure -> {
            return DomainResource.Failure(weatherResponseResult.exception)
        }
        else -> {
            return DomainResource.Failure(Exception("weather mapping error"))
        }
    }
}

fun mapToDomainVenue(venueResponseResult: Resource<VenueResponse>): DomainResource<VenueData>{
    when(venueResponseResult){
        is Resource.Success ->{
            val res = venueResponseResult.result
            if (res.response.group.results == null){
                return DomainResource.Empty
            }
            return DomainResource.Success(
                VenueData(
                    results = res.response.group.results.map{ result ->
                        Result(
                            id = result.id,
                            photo = result.photo?.let {
                                Photo(
                                    width = result.photo.width,
                                    id = result.photo.id,
                                    prefix = result.photo.prefix,
                                    suffix = it.suffix,
                                    visibility = result.photo.visibility.toBoolean()
                                )
                            },
                            venue = Venue(
                                id = result.venue.id,
                                name = result.venue.name,
                                categories = result.venue.categories.map {
                                    Category(
                                        id = it.id,
                                        name = it.name,
                                        pluralName = it.pluralName,
                                        shortName = it.shortName
                                    )
                                },
                                location = com.trifcdr.lifestylehub.domain.models.venue.venueRecommends.Location(
                                    address = result.venue.location?.address,
                                    distance = result.venue.location?.distance,
                                    formattedAddress = result.venue.location?.formattedAddress!!

                                )
                            )


                        ) },
                    totalResults = res.response.group.totalResults
                )
            )
        }
        is Resource.Failure -> {
            return DomainResource.Failure(venueResponseResult.exception)
        }
        is Resource.Unauthorized -> {
            return DomainResource.Failure(Exception("Unauthorized"))
        }
        else -> {
            return DomainResource.Failure(Exception("venue mapping error"))
        }
    }
}

fun mapToDetailsModel(detailsResponse: Resource<VenueDetailsResponse>): VenueDetailsModel?{
    when (detailsResponse){
        is Resource.Success -> {
            val res = detailsResponse.result.response
            val venue = res.venue
            val bestPhoto = venue.bestPhoto
            val hours = venue.hours
            val groups = mutableListOf<String>()
            for (el in venue.listed?.groups!!){
                for(obj in el.itemDS){
                    val photo = obj.photo
                    photo?.let {
                        groups.add("${photo.prefix}original${photo.suffix}")
                    }
                }
            }
            return venue.reasons?.items?.map {
                it.summary
            }?.let { strings ->
                VenueDetailsModel(
                    id = venue.id,
                    name = venue.name,
                    address = venue.location?.address ?: "Адрес недоступен",
                    url = venue.url ?: "",
                    bestPhoto = "${bestPhoto?.prefix}original${bestPhoto?.suffix}",
                    categories = venue.categories.map {
                        it.name
                    },
                    phone = venue.contact?.formattedPhone ?: "",
                    hoursStatus = hours?.status ?: "",
                    photos = groups,
                    reasons = strings,
                    temp = true
                )
            }
        }
        else -> {
            return null
        }
    }
}

fun mapToDomainDetail(detail: VenueDetailsModel): VenueDetailsDataDom{
    return VenueDetailsDataDom(
        id = detail.id,
        name = detail.name,
        url = detail.url,
        bestPhoto = detail.bestPhoto,
        categories = detail.categories,
        photos = detail.photos,
        phone = detail.phone,
        hoursStatus = detail.hoursStatus,
        reasons = detail.reasons,
        address = detail.address
    )
}

fun mapToDomainUser(jsonUser: String): User{
    val result = JSONObject(jsonUser).getJSONArray("results")[0] as JSONObject
    val name = result.getJSONObject("name")
    return User(
        gender = result.getString("gender"),
        firstName = name.getString("first"),
        lastName = name.getString("last"),
        email = result.getString("email"),
        username = result.getJSONObject("login").getString("username"),
        phone = result.getString("phone")
    )
}

fun mapToDomainLeisure(leisure: LeisureModel): Leisure{
    return Leisure(
        id = leisure.id,
        name = leisure.name,
        venueId = leisure.venueId,
        date = leisure.date,
        notes = leisure.notes
    )
}

fun mapToDBModel(leisure: Leisure): LeisureModel{
    return LeisureModel(
        name = leisure.name,
        venueId = leisure.venueId,
        date = leisure.date,
        notes = leisure.notes
    )
}