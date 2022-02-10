package com.example.weather.data.repositories

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.weather.data.api.WeatherApiService
import com.example.weather.data.mappers.toWeatherModel
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.entities.CurrentLocationEntities
import com.example.weather.domain.entities.WeatherModelEntities
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val service: WeatherApiService) :
    WeatherRepository {

    private var cancellationTokenSource = CancellationTokenSource()

    override fun getCurrentLocation(activity: Activity): Single<CurrentLocationEntities> {
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
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->

                        if (location != null){
                            val latitude = location.latitude.toString()
                            val longitude = location.longitude.toString()

                            emitter.onSuccess(CurrentLocationEntities("$latitude,$longitude"))
                        } else {
                            fusedLocationClient.getCurrentLocation(
                                LocationRequest.PRIORITY_HIGH_ACCURACY,
                                cancellationTokenSource.token
                            )
                                .addOnSuccessListener { location ->
                                    val latitude = location.latitude.toString()
                                    val longitude = location.longitude.toString()

                                    emitter.onSuccess(CurrentLocationEntities("$latitude,$longitude"))
                                }
                        }
                    }

            } else {

                emitter.onError(Throwable("No permission"))
            }
        }
    }

    override fun getWeather(query: String): Single<WeatherModelEntities> {
        return service.requestWeatherForCity(query)
            .map { it.body()?.toWeatherModel() }
    }
}

