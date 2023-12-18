package hh.school.lesson_12_zemskov.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Divorce(
    val start: String,
    val end: String
) : Parcelable {
    override fun toString(): String {
        return "$start – $end"
    }
}