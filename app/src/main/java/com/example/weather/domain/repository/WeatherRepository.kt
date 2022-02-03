package com.example.weather.domain.repository

import com.example.weather.data.models.Weather
import io.reactivex.Observable
import retrofit2.Response


interface WeatherRepository {
    fun getWeather(q: String): Observable<Response<Weather>>
}