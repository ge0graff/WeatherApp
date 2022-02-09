package com.example.weather.ui
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.weather.R
import com.example.weather.core.appComponent
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.entities.WeatherModelEntities
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import coil.load


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
        mPresenter?.checkNetworkConnection()
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.requestPermission(this)
        bindingMain.updateWeatherButton.setOnClickListener {
            mPresenter?.loadData()
            Toast.makeText(this, resources.getString(R.string.data_update), Toast.LENGTH_SHORT).show()
        }
    }

    override fun setupWeather(weatherModel: WeatherModelEntities) {
        bindingMain.imageView.load("https:${weatherModel.icon}")
        bindingMain.name.text = weatherModel.name
        bindingMain.region.text = weatherModel.region
        bindingMain.temp.text = resources.getString(R.string.main_activity_temp, weatherModel.tempC.toString())
        bindingMain.wind.text = resources.getString(R.string.main_activity_wind, weatherModel.windKph.toString())

    }

    override fun showWeather() {
        bindingMain.temp.visibility = View.VISIBLE
        bindingMain.wind.visibility = View.VISIBLE
        bindingMain.errorMsg.visibility = View.INVISIBLE
    }

    override fun showDialog() {
        val listener = DialogInterface.OnClickListener { dialog, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE ->
                {
                    dialog.dismiss()
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }
            }
        }
        val dialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setMessage(resources.getString(R.string.no_permission))
            .setPositiveButton(resources.getString(R.string.ok), listener)
            .create()
        dialog.show()
    }

    override fun showApp() {
        bindingMain.appProgressBar.visibility = View.GONE
        bindingMain.cardView.visibility = View.VISIBLE
    }

    override fun showError() {
        bindingMain.errorMsg.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
    }

}

