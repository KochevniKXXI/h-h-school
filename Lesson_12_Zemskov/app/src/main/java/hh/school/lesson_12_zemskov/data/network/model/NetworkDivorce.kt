package hh.school.lesson_12_zemskov.data.network.model

import com.google.gson.annotations.SerializedName
import hh.school.lesson_12_zemskov.ui.model.Divorce

data class NetworkDivorce(
    @SerializedName("start") val start: String?,
    @SerializedName("end") val end: String?
) {
    fun asInternalModel() = Divorce(
        start = start.orEmpty(),
        end = end.orEmpty()
    )
}
