package com.example.weather.data.mappers

import com.example.weather.data.models.Weather
import com.example.weather.domain.repository.common.WeatherModel


fun Weather.toWeatherModel() = with(this) {
    WeatherModel(location.name, location.region, current.tempC, current.windMph, current.condition.icon)
}