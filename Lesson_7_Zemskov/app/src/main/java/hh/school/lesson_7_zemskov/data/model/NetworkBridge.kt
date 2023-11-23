package hh.school.lesson_7_zemskov.data.model

import com.google.gson.annotations.SerializedName
import hh.school.lesson_7_zemskov.model.Bridge

data class NetworkBridge(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("divorces") val divorces: List<NetworkDivorce>?,
    @SerializedName("photo_close_url") val photoCloseUrl: String?,
    @SerializedName("photo_open_url") val photoOpenUrl: String?
)

fun NetworkBridge.asInternalModel() = Bridge(
    id = id ?: 0,
    name = name ?: "",
    description = description ?: "",
    divorces = divorces?.map { it.asInternalModel() } ?: listOf(),
    photoCloseUrl = photoCloseUrl ?: "",
    photoOpenUrl = photoOpenUrl ?: ""
)
