package com.example.weather.domain.repository.common

data class WeatherModel(
    val name: String,
    val region: String,
    val tempC: Double,
    val windKph: Double,
    val icon: String
)
