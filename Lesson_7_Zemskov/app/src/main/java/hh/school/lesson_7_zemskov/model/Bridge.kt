package hh.school.lesson_7_zemskov.model

data class Bridge(
    val id: Int,
    val name: String,
    val description: String,
    val divorces: List<Divorce>,
    val photoCloseUrl: String,
    val photoOpenUrl: String
)
