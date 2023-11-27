package hh.school.lesson_9_zemskov

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import hh.school.lesson_9_zemskov.databinding.ActivityMainBinding
import hh.school.lesson_9_zemskov.model.Weather
import hh.school.lesson_9_zemskov.services.DownloadService
import hh.school.lesson_9_zemskov.services.WeatherService

private const val URL =
    "https://www.dropbox.com/scl/fi/4kawv3zu3eloi7ympyzow/photo_visokogo_razresheniya.zip?rlkey=ysdt9ser2mmh6hzyy74jw8guc&dl=1"

class MainActivity : AppCompatActivity(), WeatherServiceCallbacks {

    private lateinit var binding: ActivityMainBinding
    private var weatherService: WeatherService? = null
    private val requestManager by lazy { Glide.with(this) }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, binder: IBinder?) {
            weatherService = (binder as WeatherService.WeatherBinder).service.apply {
                serviceCallbacks = this@MainActivity
            }
            weatherService?.getWeather()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            weatherService = null
        }
    }

    private val progressReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.getIntExtra(DownloadService.KEY_PROGRESS, 0)?.let {
                binding.linearProgressIndicator.setProgressCompat(it, true)
                binding.buttonDownloadFile.apply {
                    isEnabled = false
                    text = getString(R.string.button_download_file_loading_text)
                }
            }
            intent?.getStringExtra(DownloadService.KEY_FILE_URI)?.let {
                requestManager.load(it).into(binding.imageViewDownloadImage)
                binding.buttonDownloadFile.apply {
                    isEnabled = true
                    text = getString(R.string.button_download_file_text)
                }
            }
        }
    }

    companion object {
        const val PROGRESS_RECEIVER_ACTION = "hh.school.lesson_9_zemskov.PROGRESS_RECEIVER_ACTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonDownloadFile.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(DownloadService.createStartIntent(this, URL))
            } else {
                startService(DownloadService.createStartIntent(this, URL))
            }
        }

        ContextCompat.registerReceiver(
            this,
            progressReceiver,
            IntentFilter(PROGRESS_RECEIVER_ACTION),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onStart() {
        super.onStart()
        Intent(this, WeatherService::class.java).also { intent ->
            bindService(intent, connection, BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        if (weatherService != null) {
            weatherService?.serviceCallbacks = null
            unbindService(connection)
            weatherService = null
        }
        super.onDestroy()
    }

    override fun displayWeather(weather: Weather?) = runOnUiThread {
        with(binding) {
            textViewCity.text = weather?.name
            textViewTemperature.text = getString(R.string.temperature, weather?.temp)
            requestManager.load(getString(R.string.image_url, weather?.icon))
                .into(imageWeather)
            textViewWind.text = getString(R.string.wind, weather?.speedWind)
            textViewHumidity.text = getString(R.string.humidity, weather?.humidity)
            textViewPressure.text = getString(R.string.pressure, weather?.pressure)
            thermometerView.mercuryLevel = weather?.temp?.toFloat() ?: 0f
        }
    }
}