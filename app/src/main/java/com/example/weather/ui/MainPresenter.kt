package com.example.weather.ui

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.example.weather.domain.NetworkConnect
import com.example.weather.domain.repository.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val mView: MainContact.MainView,
    private val weatherRepository: WeatherRepository,
    private val activity: MainActivity
    ): MainContact.MainPresenter {

    private val disposable = CompositeDisposable()


    override fun loadData() {
        checkNetworkConnection()
        disposable.add(
            weatherRepository.getWeather(weatherRepository.getCurrentLocation(activity).currentLocation)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe({
                      mView.showWeather()
                      mView.setupWeather(it)
                  }, {
                      Log.d("Error", it.toString())
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

                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    PERMISSION_GRANTED

                }
                else -> {
                    PERMISSION_DENIED
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

    private fun checkNetworkConnection() {

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













