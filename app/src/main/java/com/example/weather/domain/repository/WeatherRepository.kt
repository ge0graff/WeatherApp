package com.example.weather.domain.repository

import com.example.weather.data.models.Weather
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response


interface WeatherRepository {
    fun getWeather(q: String): Single<Response<Weather>>
}