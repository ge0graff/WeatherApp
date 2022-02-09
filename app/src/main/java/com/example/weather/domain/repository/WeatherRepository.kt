package com.example.weather.domain.repository

import android.app.Activity
import com.example.weather.domain.entities.CurrentLocationEntities
import com.example.weather.domain.entities.WeatherModelEntities
import io.reactivex.Single


interface WeatherRepository {
    fun getWeather(query: String): Single<WeatherModelEntities>
    fun getCurrentLocation(activity: Activity): Single<CurrentLocationEntities>
}