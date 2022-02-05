package com.example.weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather.core.App
import com.example.weather.core.appComponent
import com.example.weather.data.repositoriesImpl.WeatherRepositoryImpl
import com.example.weather.di.components.AppComponent
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.repository.common.WeatherModel
import javax.inject.Inject

class MainActivity: AppCompatActivity(), MainContact.MainView {

    @Inject
    lateinit var weatherRepository: WeatherRepository
    private var mPresenter: MainContact.MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(com.example.weather.R.layout.activity_main)

        mPresenter = MainPresenter(this, weatherRepository)

        mPresenter?.loadData()
    }

    override fun setupWeather(weatherModel: WeatherModel) {

    }

}

