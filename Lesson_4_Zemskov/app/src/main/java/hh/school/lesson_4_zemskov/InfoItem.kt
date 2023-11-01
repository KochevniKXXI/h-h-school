package hh.school.lesson_4_zemskov

sealed interface InfoItem {
    val iconResourceId: Int
    val title: String

    class BaseInfoItem(
        override val iconResourceId: Int,
        override val title: String
    ) : InfoItem

    class DetailInfoItem(
        override val iconResourceId: Int,
        override val title: String,
        val description: String
    ) : InfoItem
}