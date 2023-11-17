package hh.school.lesson_7_zemskov.data

import hh.school.lesson_7_zemskov.data.model.NetworkBridge
import retrofit2.http.GET
import retrofit2.http.Path

interface BridgesApiService {

    @GET("bridges")
    suspend fun getBridges(): List<NetworkBridge>

    @GET("bridges/{id}")
    suspend fun getBridge(@Path("id") id: Int): NetworkBridge
}