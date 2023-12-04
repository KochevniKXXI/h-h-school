package hh.school.lesson_10_zemskov.model

data class Divorce(
    val start: String,
    val end: String
) {
    override fun toString(): String {
        return "$start – $end"
    }
}
