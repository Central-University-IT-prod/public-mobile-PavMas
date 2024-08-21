package com.trifcdr.lifestylehub.domain.models.weather

data class Main(
    val feelsLike: Double,
    val grndLevel: Int? = null,
    val humidity: Int,
    val pressure: Int,
    val seaLevel: Int? = null,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double
)