package hh.school.lesson_9_zemskov

import hh.school.lesson_9_zemskov.model.Weather

interface WeatherServiceCallbacks {
    fun displayWeather(weather: Weather?)
}