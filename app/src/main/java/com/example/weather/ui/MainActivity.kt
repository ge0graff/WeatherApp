package com.example.weather.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.core.appComponent
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.repository.common.WeatherModel
import javax.inject.Inject

class MainActivity: AppCompatActivity(), MainContact.MainView {

    @Inject
    lateinit var weatherRepository: WeatherRepository

    private var mPresenter: MainContact.MainPresenter? = null

    private lateinit var bindingMain: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        mPresenter = MainPresenter(this, weatherRepository, this)

        mPresenter?.loadData()

    }

    override fun onStart() {
        super.onStart()
        mPresenter?.requestPermission(this)
        bindingMain.updateWeatherButton.setOnClickListener {
            mPresenter?.loadData()
        }


    }


    override fun setupWeather(weatherModel: WeatherModel) {

        Glide.with(this)
            .load("https:${weatherModel.icon}")
            .centerCrop()
            .into(bindingMain.imageView)

        bindingMain.name.text = weatherModel.name
        bindingMain.region.text = weatherModel.region
        bindingMain.temp.text = resources.getString(R.string.main_activity_temp, weatherModel.tempC.toString())
        bindingMain.wind.text = resources.getString(R.string.main_activity_wind, weatherModel.windKph.toString())

    }

    override fun showWeather() {
        bindingMain.temp.visibility = View.VISIBLE
        bindingMain.wind.visibility = View.VISIBLE
        bindingMain.errorMsg.visibility = View.GONE
    }

    override fun showError() {
        bindingMain.temp.visibility = View.GONE
        bindingMain.wind.visibility = View.GONE
        bindingMain.errorMsg.visibility = View.VISIBLE
    }



}

