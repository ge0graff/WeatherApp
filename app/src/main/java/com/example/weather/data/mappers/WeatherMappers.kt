package com.example.weather.data.mappers

import com.example.weather.data.models.Weather
import com.example.weather.domain.entities.WeatherModelEntities


fun Weather.toWeatherModel() = with(this) {
    WeatherModelEntities(location.name, location.region, current.tempC, current.windMph, current.condition.icon)
}