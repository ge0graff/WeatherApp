package com.example.weather.data.api

import com.example.weather.data.models.Weather
import io.reactivex.Observable
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {

    @GET("current.json?key=77c98da53a664007806123649222101&aqi=no")
    fun requestWeatherForCity(
        @Query("q") q: String
    ): Observable<Response<Weather>>
}