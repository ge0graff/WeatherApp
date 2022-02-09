package com.example.weather.domain.entities

data class WeatherModelEntities(
    val name: String,
    val region: String,
    val tempC: Double,
    val windKph: Double,
    val icon: String
)
