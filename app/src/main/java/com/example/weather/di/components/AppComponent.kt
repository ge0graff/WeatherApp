package com.example.weather.di.components

import com.example.weather.di.modules.AppModule
import com.example.weather.di.modules.NetworkModule
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.ui.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    val weatherRepository: WeatherRepository

}