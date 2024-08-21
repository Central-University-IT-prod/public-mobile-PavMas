package com.trifcdr.lifestylehub.domain.usecase

import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.location.UserLocation
import com.trifcdr.lifestylehub.domain.models.weather.WeatherData
import com.trifcdr.lifestylehub.domain.repositories.WeatherRepository

/**
 * Created by trifcdr.
 */
class GetWeatherByLocationUseCase(
    private val repository: WeatherRepository
) {

    suspend fun execute(userLocation: UserLocation) : DomainResource<WeatherData> {
        return repository.getWeatherByLocation(userLocation)
    }

}