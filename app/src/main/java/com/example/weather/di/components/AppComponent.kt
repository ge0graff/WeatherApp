package com.example.weather.di.components

import com.example.weather.di.modules.AppModule
import com.example.weather.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

}