package com.example.weather.di.modules

import com.example.weather.data.api.WeatherApiService
import com.example.weather.data.repositoriesImpl.WeatherRepositoryImpl
import com.example.weather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideWeatherRepositoryImpl(service: WeatherApiService): WeatherRepository {
        return WeatherRepositoryImpl(service)
    }
}