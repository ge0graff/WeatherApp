package com.example.weather.ui

import com.example.weather.domain.repository.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val mView: MainContact.MainView,
    private val weatherRepository: WeatherRepository
    ): MainContact.MainPresenter {

    private val disposable = CompositeDisposable()

    override fun loadData() {
        disposable.add(
            weatherRepository.getWeather("48.8567,2.3508")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView.setupWeather(it)
                },{
                    it
                })
        )
    }

}









