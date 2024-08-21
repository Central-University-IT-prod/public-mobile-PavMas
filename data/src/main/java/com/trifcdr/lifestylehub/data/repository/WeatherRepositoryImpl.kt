package com.trifcdr.lifestylehub.data.repository

import com.trifcdr.lifestylehub.data.network.api.weather.WeatherApi
import com.trifcdr.lifestylehub.data.mappers.mapToDomainWeather
import com.trifcdr.lifestylehub.domain.models.location.UserLocation
import com.trifcdr.lifestylehub.domain.models.weather.WeatherData
import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.repositories.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherByLocation(userLocation: UserLocation): DomainResource<WeatherData> {
        return mapToDomainWeather(weatherApi.getWeatherByLocation(userLocation))
    }
}