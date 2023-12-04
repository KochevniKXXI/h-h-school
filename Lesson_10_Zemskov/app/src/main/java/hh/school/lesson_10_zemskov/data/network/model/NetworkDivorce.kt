package hh.school.lesson_10_zemskov.data.network.model

import com.google.gson.annotations.SerializedName
import hh.school.lesson_10_zemskov.model.Divorce

data class NetworkDivorce(
    @SerializedName("start") val start: String?,
    @SerializedName("end") val end: String?
) {
    fun asInternalModel() = Divorce(
        start = start ?: "",
        end = end ?: ""
    )
}
