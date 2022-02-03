package com.example.weather.data.repositoriesImpl

import com.example.weather.data.api.WeatherApiService
import com.example.weather.data.models.Weather
import com.example.weather.domain.repository.WeatherRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

import javax.inject.Inject

class WeatherRepositoryImpl (private val service: WeatherApiService): WeatherRepository {

    override fun getWeather(q: String): Observable<Response<Weather>> {
        return service.requestWeatherForCity(q)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it }
    }
}

