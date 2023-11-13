package hh.school.lesson_6_zemskov.model

import androidx.annotation.DrawableRes

sealed interface Service {
    val name: String
    val serialNumber: String
    @get:DrawableRes
    val iconResId: Int
    val nextCalculationDate: String
    val metersDataDate: String

    data class SingleMeterService(
        override val name: String,
        override val serialNumber: String,
        override val iconResId: Int,
        override val nextCalculationDate: String = "",
        override val metersDataDate: String = ""
    ) : Service

    data class DayNightMaxService(
        override val name: String,
        override val serialNumber: String,
        override val iconResId: Int,
        override val nextCalculationDate: String = "",
        override val metersDataDate: String = ""
    ) : Service
}
