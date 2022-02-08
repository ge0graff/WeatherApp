package com.example.weather.data.repositoriesImpl

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.weather.data.api.WeatherApiService
import com.example.weather.data.mappers.toWeatherModel
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.repository.common.CurrentLocationModel
import com.example.weather.domain.repository.common.WeatherModel
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val service: WeatherApiService) :
    WeatherRepository {


    private var currentLocation: String = ""

    private var cancellationTokenSource = CancellationTokenSource()

    override fun getCurrentLocation(activity: Activity): Single<CurrentLocationModel> {
        return Single.create { emitter ->
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.getCurrentLocation(
                    LocationRequest.PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource.token
                )
                    .addOnSuccessListener { location ->
                        val latitude = location.latitude.toString()
                        val longitude = location.longitude.toString()

                        emitter.onSuccess(CurrentLocationModel("$latitude,$longitude"))

                        Log.d("Location", "$latitude,$longitude")
                        Log.d("Location", currentLocation)
                    }

            } else {
                emitter.onError(Throwable("No permission"))
            }
        }
    }

    fun getCurrentLocation2(
        activity: Activity,
        resultCallback: (CurrentLocationModel) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )
                .addOnSuccessListener { location ->
                    val latitude = location.latitude.toString()
                    val longitude = location.longitude.toString()

                    resultCallback(CurrentLocationModel("$latitude,$longitude"))

                    Log.d("Location", "$latitude,$longitude")
                    Log.d("Location", currentLocation)
                }

        } else {
            errorCallback(Throwable("No permission"))
        }

    }


    override fun getWeather(query: String): Single<WeatherModel> {
        return service.requestWeatherForCity(query)
            .map { it.body()?.toWeatherModel() }
    }
}

