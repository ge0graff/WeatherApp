package com.example.weather.di.components

import com.example.weather.data.repositoriesImpl.WeatherRepositoryImpl
import com.example.weather.di.modules.DataModule
import dagger.Component

@Component(modules = [DataModule::class])
interface AppComponent {

    fun weatherRepositoryImpl(): WeatherRepositoryImpl
}