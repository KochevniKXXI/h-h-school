package hh.school.lesson_9_zemskov.model

data class Weather(
    val name: String,
    val temp: Int?,
    val icon: String,
    val weatherCondition: String,
    val speedWind: Int?,
    val humidity: Int?,
    val pressure: Int?
)
