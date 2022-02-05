package com.example.weather.data.repositoriesImpl

import com.example.weather.data.api.WeatherApiService
import com.example.weather.data.mappers.toWeatherModel
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.repository.common.WeatherModel
import io.reactivex.Single

class WeatherRepositoryImpl (private val service: WeatherApiService): WeatherRepository {

    override fun getWeather(query: String): Single<WeatherModel>{
        return service.requestWeatherForCity(query)
            .map { it.body()?.toWeatherModel() }
    }
}

