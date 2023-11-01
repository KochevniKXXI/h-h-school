package hh.school.lesson_4_zemskov.data

import hh.school.lesson_4_zemskov.InfoItem
import hh.school.lesson_4_zemskov.R

object DataSource {
    fun listInfoItems() = listOf(
        InfoItem.DetailInfoItem(
            R.drawable.ic_bill,
            "Квитанции",
            "- 40 080,55 ₽"
        ),
        InfoItem.DetailInfoItem(
            R.drawable.ic_counter,
            "Счётчики",
            "Подайте показания"
        ),
        InfoItem.DetailInfoItem(
            R.drawable.ic_installment,
            "Рассрочка",
            "Сл. платёж 02.11.2023"
        ),
        InfoItem.DetailInfoItem(
            R.drawable.ic_insurance,
            "Страхование",
            "Полис до 13.01.2024"
        ),
        InfoItem.DetailInfoItem(
            R.drawable.ic_tv,
            "Интернет и ТВ",
            "Баланс 777 ₽"
        ),
        InfoItem.DetailInfoItem(
            R.drawable.ic_homephone,
            "Домофон",
            "Подключён"
        ),
        InfoItem.DetailInfoItem(
            R.drawable.ic_guard,
            "Охрана",
            "Нет"
        ),
        InfoItem.BaseInfoItem(
            R.drawable.ic_uk_contacts,
            "Контакты УК и служб"
        ),
        InfoItem.BaseInfoItem(
            R.drawable.ic_request,
            "Мои заявки"
        ),
        InfoItem.BaseInfoItem(
            R.drawable.ic_about,
            "Памятка жителя А101"
        )
    )
}