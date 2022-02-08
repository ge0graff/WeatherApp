package com.example.weather.ui

import com.example.weather.domain.repository.common.WeatherModel


interface MainContact {

    interface MainPresenter {
        fun loadData()
        fun requestPermission(activity: MainActivity)
    }

    interface MainView {
        fun setupWeather(weatherModel: WeatherModel)
        fun showError()
        fun showWeather()

    }

}
