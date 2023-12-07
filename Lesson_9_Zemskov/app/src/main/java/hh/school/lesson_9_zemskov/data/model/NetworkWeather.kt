package hh.school.lesson_9_zemskov.data.model

import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

data class NetworkWeather(
    @SerializedName("name") val name: String?,
    @SerializedName("weather") val weather: List<Weather>?,
    @SerializedName("main") val main: Main?,
    @SerializedName("wind") val wind: Wind?
) {
    fun asInternalModel() = hh.school.lesson_9_zemskov.model.Weather(
        name = name ?: "",
        temp = main?.temp?.roundToInt(),
        icon = weather?.firstOrNull()?.icon ?: "",
        weatherCondition = weather?.firstOrNull()?.main ?: "",
        speedWind = wind?.speed?.roundToInt(),
        humidity = main?.humidity,
        pressure = main?.pressure
    )
}

data class Main(
    @SerializedName("temp") val temp: Float?,
    @SerializedName("pressure") val pressure: Int?,
    @SerializedName("humidity") val humidity: Int?
)

data class Weather(
    @SerializedName("main") val main: String?,
    @SerializedName("icon") val icon: String?
)

data class Wind(
    @SerializedName("speed") val speed: Float?
)