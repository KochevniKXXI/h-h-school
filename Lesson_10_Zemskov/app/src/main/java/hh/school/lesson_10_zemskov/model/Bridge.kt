package hh.school.lesson_10_zemskov.model

data class Bridge(
    val id: Int,
    val name: String,
    val divorces: List<Divorce>,
    val lat: Double?,
    val lng: Double?
) {
    fun getDivorcesAsString(): String {
        return divorces.fold("") { acc, divorce ->
            "$acc    $divorce"
        }.trim()
    }
}
