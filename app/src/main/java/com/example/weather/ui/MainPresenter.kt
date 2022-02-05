package com.example.weather.ui

import com.example.weather.domain.repository.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(
    private val mView: MainContact.MainView,
    private val weatherRepository: WeatherRepository
    ): MainContact.MainPresenter {

    private val disposable = CompositeDisposable()
    var name: String? = null
    var region: String? = null
    var tempC: Double? = null
    var windKph: Double? = null
    var icon: String? = null


    init {
        disposable.add(
            weatherRepository.getWeather("48.8567,2.3508")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    name = it.body()?.location?.name
                    region = it.body()?.location?.region
                    tempC = it.body()?.current?.tempC
                    windKph = it.body()?.current?.windKph
                    icon = it.body()?.current?.condition?.icon
                },{
                    it
                })
        )
    }

    val data = listOf(name, region, tempC, windKph, icon)


}









