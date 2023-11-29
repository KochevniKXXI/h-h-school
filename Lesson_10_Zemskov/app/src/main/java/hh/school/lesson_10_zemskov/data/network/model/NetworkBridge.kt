package hh.school.lesson_10_zemskov.data.network.model

import com.google.gson.annotations.SerializedName
import hh.school.lesson_10_zemskov.model.Bridge

data class NetworkBridge(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("divorces") val divorces: List<NetworkDivorce>?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lng") val lng: Double?
) {
    fun asInternalModel() = Bridge(
        id = id ?: 0,
        name = name ?: "",
        divorces = divorces?.map { it.asInternalModel() } ?: listOf(),
        lat = lat,
        lng = lng
        )
}
