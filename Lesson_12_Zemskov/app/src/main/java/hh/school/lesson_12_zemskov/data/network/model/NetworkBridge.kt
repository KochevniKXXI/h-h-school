package hh.school.lesson_12_zemskov.data.network.model

import com.google.gson.annotations.SerializedName
import hh.school.lesson_12_zemskov.ui.model.Bridge

data class NetworkBridge(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("divorces") val divorces: List<NetworkDivorce>?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lng") val lng: Double?,
    @SerializedName("photo_close_url") val photoCloseUrl: String?,
    @SerializedName("photo_open_url") val photoOpenUrl: String?
) {
    fun asInternalModel() = Bridge(
        id = id ?: 0,
        name = name.orEmpty(),
        description = description.orEmpty(),
        divorces = divorces?.map { it.asInternalModel() } ?: listOf(),
        lat = lat,
        lng = lng,
        photoCloseUrl = photoCloseUrl.orEmpty(),
        photoOpenUrl = photoOpenUrl.orEmpty()
    )
}