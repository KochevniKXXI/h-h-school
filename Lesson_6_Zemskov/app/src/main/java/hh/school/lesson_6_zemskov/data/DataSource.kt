package hh.school.lesson_6_zemskov.data

import hh.school.lesson_6_zemskov.R
import hh.school.lesson_6_zemskov.model.Service

object DataSource {
    val listServices = listOf(
        Service.SingleMeterService(
            name = "Холодная вода",
            serialNumber = "12345678",
            iconResId = R.drawable.ic_water_cold,
            nextCalculationDate = "31.12.23"
        ),
        Service.SingleMeterService(
            name = "Горячая вода",
            serialNumber = "87654321",
            iconResId = R.drawable.ic_water_hot,
            nextCalculationDate = "31.12.23"
        ),
        Service.DayNightMaxService(
            name = "Электроэнергия",
            serialNumber = "56781234",
            iconResId = R.drawable.ic_electro_copy,
            nextCalculationDate = "21.11.23",
            metersDataDate = "11.11.23"
        )
    )

    val listPictures = listOf(
        "https://img.freepik.com/free-photo/flat-lay-of-volleyball-on-the-beach-sand_23-2148662653.jpg" to "Мяч",
        "https://img.freepik.com/free-photo/volleyball-net-at-the-sandy-beach-on-a-bright-sunny-day_181624-5357.jpg" to "Площадки",
        "https://img.freepik.com/free-photo/back-view-of-woman-signaling-teammate-with-hands-while-playing-volleyball_23-2148662648.jpg" to "Партия"
    )
}