package hh.school.lesson_7_zemskov.data.model

import com.google.gson.annotations.SerializedName
import hh.school.lesson_7_zemskov.model.Divorce

data class NetworkDivorce(
    @SerializedName("start") val start: String?,
    @SerializedName("end") val end: String?
)

fun NetworkDivorce.asInternalModel() = Divorce(
    start = start ?: "",
    end = end ?: ""
)
