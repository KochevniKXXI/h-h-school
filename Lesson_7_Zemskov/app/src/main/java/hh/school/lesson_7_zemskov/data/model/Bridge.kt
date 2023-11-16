package hh.school.lesson_7_zemskov.data.model

import com.google.gson.annotations.SerializedName

data class Bridge(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("divorces") val divorces: List<Divorce>?,
    @SerializedName("photo_close_url") val photoCloseUrl: String?,
    @SerializedName("photo_open_url") val photoOpenUrl: String?
)
