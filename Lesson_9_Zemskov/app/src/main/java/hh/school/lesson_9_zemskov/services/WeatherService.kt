package hh.school.lesson_9_zemskov.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import hh.school.lesson_9_zemskov.WeatherServiceCallbacks
import hh.school.lesson_9_zemskov.data.WeatherApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class WeatherService : Service() {

    private val job = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + job)
    var serviceCallbacks: WeatherServiceCallbacks? = null
    private var weatherStream = flow {
        while (true) {
            val weather = try {
                WeatherApiClient.apiService.getWeather().asInternalModel()
            } catch (e: Exception) {
                null
            }
            emit(weather)
            delay(60_000)
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return WeatherBinder()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    inner class WeatherBinder : Binder() {
        val service: WeatherService
            get() = this@WeatherService
    }

    fun getWeather() {
        serviceScope.launch {
            weatherStream.collect {
                serviceCallbacks?.displayWeather(it)
            }
        }
    }
}
