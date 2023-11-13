package hh.school.lesson_5_zemskov.data

import hh.school.lesson_5_zemskov.model.ServiceItem

object DataSource {
    val listServices = listOf(
        ServiceItem(
            "Царь пышка",
            "Скидка 10% на выпечку по коду",
            "Лермонтовский пр, 52, МО №1",
            "https://img.freepik.com/free-photo/cute-filled-donuts-in-top-view_23-2148332764.jpg"
        ),
        ServiceItem(
            "Химчистка «МАЙ?»",
            "Скидка 5% на чистку пальто",
            "Лермонтовский пр, 48",
            "https://img.freepik.com/free-photo/still-life-say-no-to-fast-fashion_23-2149669580.jpg"
        ),
        ServiceItem(
            "Шаверма У Ашота ",
            "При покупке шавермы, фалафель бесплатно",
            "Лермонтовский пр, 52, МО №1",
            "https://img.freepik.com/free-photo/side-view-shawarma-with-fried-potatoes-in-board-cookware_176474-3215.jpg"
        )
    )
}