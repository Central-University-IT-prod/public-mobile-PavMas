package com.trifcdr.lifestylehub.data.network.api.weather

import com.example.lifestylehub.data.Resource
import com.trifcdr.lifestylehub.domain.models.location.UserLocation
import com.trifcdr.lifestylehub.data.network.models.weatherModels.WeatherResponse

/**
 * Created by trifcdr.
 */
interface WeatherApi {

    suspend fun getWeatherByLocation(userLocation: UserLocation) : Resource<WeatherResponse>


}