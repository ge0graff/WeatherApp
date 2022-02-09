package com.example.weather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.example.weather.domain.NetworkConnect
import com.example.weather.domain.repository.WeatherRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val mView: MainContact.MainView,
    private val weatherRepository: WeatherRepository,
    private val activity: MainActivity
    ): MainContact.MainPresenter {

    private val disposable = CompositeDisposable()


    override fun loadData() {
        Log.d("Test", "repeat")
        disposable.add(
            weatherRepository.getCurrentLocation(activity)
                .flatMap { weatherRepository.getWeather(it.currentLocation).subscribeOn(Schedulers.io()) }
                .repeatWhen { completed -> completed.delay(30, TimeUnit.SECONDS) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("Test", "Repeat2")
                    mView.showWeather()
                    mView.setupWeather(it)
                }, {
                    checkNetworkConnection()
                    Log.d("Test", it.toString())
                })
                )
            }


    override fun requestPermission(activity: MainActivity) {
        val locationPermissionRequest = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    PERMISSION_GRANTED
                    Log.d("Test", "Granted")
                    loadData()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    PERMISSION_GRANTED
                    Log.d("Test", "Granted2")
                    loadData()
                }
                else -> {
                    PERMISSION_DENIED
                    Log.d("Test", "Denied")
                    mView.showDialog()
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun checkNetworkConnection() {

        val networkConnectManager = NetworkConnect(activity)

        if (networkConnectManager.isOnline()) {
            mView.showWeather()
        } else {
            mView.showError()
        }
    }



}





//    fun checkPermission() {
//        context.checkSelfPermission(ACCESS_FINE_LOCATION) == PERMISSION_DENIED
//    }













