package com.trifcdr.lifestylehub.domain.models.weather

/**
 * Created by trifcdr.
 */
data class WeatherData(
    val id: Int,
    val main: Main,
    val name: String,
    val weather: List<Weather>,
)