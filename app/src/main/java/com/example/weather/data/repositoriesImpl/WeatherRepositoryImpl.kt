package com.example.weather.data.repositoriesImpl

import com.example.weather.data.api.WeatherApiService
import com.example.weather.data.models.Weather
import com.example.weather.domain.repository.WeatherRepository
import io.reactivex.Single
import retrofit2.Response

class WeatherRepositoryImpl (private val service: WeatherApiService): WeatherRepository {

    override fun getWeather(q: String): Single<Response<Weather>> {
        return service.requestWeatherForCity(q)
    }
}

