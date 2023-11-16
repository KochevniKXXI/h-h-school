package hh.school.lesson_7_zemskov.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Divorce(
    @SerializedName("start") val start: String?,
    @SerializedName("end") val end: String?
) : Parcelable {
    override fun toString(): String {
        return "$start – $end"
    }
}
