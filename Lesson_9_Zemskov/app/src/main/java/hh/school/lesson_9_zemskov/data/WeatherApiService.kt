package hh.school.lesson_9_zemskov.data

import hh.school.lesson_9_zemskov.data.model.NetworkWeather
import retrofit2.http.GET

interface WeatherApiService {
    @GET("weather?q=saratov&units=metric&appid=a924f0f5b30839d1ecb95f0a6416a0c2")
    suspend fun getWeather(): NetworkWeather
}