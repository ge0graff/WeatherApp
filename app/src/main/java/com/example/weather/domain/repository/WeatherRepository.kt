package com.example.weather.domain.repository

import android.app.Activity
import com.example.weather.domain.repository.common.CurrentLocationModel
import com.example.weather.domain.repository.common.WeatherModel
import com.example.weather.ui.MainActivity
import io.reactivex.Observable
import io.reactivex.Single


interface WeatherRepository {
    fun getWeather(query: String): Single<WeatherModel>
    fun getCurrentLocation(activity: Activity): Single<CurrentLocationModel>
}