package com.example.weather.ui

import com.example.weather.domain.entities.WeatherModelEntities


interface MainContact {

    interface MainPresenter {
        fun loadData()
        fun requestPermission(activity: MainActivity)
        fun checkNetworkConnection()
        fun onDestroy()
    }

    interface MainView {
        fun setupWeather(weatherModel: WeatherModelEntities)
        fun showError()
        fun showWeather()
        fun showDialog()
        fun showApp()
    }

}
