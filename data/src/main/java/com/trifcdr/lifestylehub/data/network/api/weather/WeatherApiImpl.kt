package com.trifcdr.lifestylehub.data.network.api.weather

import com.example.lifestylehub.data.Resource
import com.example.lifestylehub.data.network.BASE_URL_OPENWEATHER
import com.example.lifestylehub.data.network.KEY_WEATHER
import com.trifcdr.lifestylehub.data.network.models.weatherModels.WeatherResponse
import com.trifcdr.lifestylehub.domain.models.location.UserLocation
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class WeatherApiImpl(
    private val httpClient: HttpClient
) : WeatherApi {
    override suspend fun getWeatherByLocation(userLocation: UserLocation): Resource<WeatherResponse> {
        return try{
            Resource.Success(
                httpClient.get<WeatherResponse> {
                    url(WEATHER)
                    parameter("appid", KEY_WEATHER)
                    parameter("units", "metric")
                    parameter("lang", "RU")
                    parameter("lat", userLocation.lat)
                    parameter("lon", userLocation.lon)
                }
            )
        }
        catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    companion object{
        private const val WEATHER = "$BASE_URL_OPENWEATHER/data/2.5/weather"
    }
}