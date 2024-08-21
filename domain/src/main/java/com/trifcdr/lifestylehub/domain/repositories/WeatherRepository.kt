package com.trifcdr.lifestylehub.domain.repositories

import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.location.UserLocation
import com.trifcdr.lifestylehub.domain.models.weather.WeatherData

/**
 * Created by trifcdr.
 */
interface WeatherRepository {

    suspend fun getWeatherByLocation(userLocation: UserLocation) : DomainResource<WeatherData>



}