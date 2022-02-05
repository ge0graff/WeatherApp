package com.example.weather.domain.repository

import com.example.weather.data.models.Weather
import com.example.weather.domain.repository.common.WeatherModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response


interface WeatherRepository {
    fun getWeather(q: String): Single<WeatherModel>
}