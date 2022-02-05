package com.example.weather.core

import android.app.Application
import android.content.Context
import com.example.weather.di.components.AppComponent
import com.example.weather.di.components.DaggerAppComponent
import dagger.internal.DaggerCollections

class App: Application() {

    private var _appComponent: AppComponent? = null

    internal val appComponent: AppComponent
        get() = checkNotNull(_appComponent) {
            "AppComponent isn't initialized"
        }

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.create()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }


