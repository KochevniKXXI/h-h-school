package hh.school.lesson_12_zemskov.data.network

import hh.school.lesson_12_zemskov.data.network.model.NetworkBridge
import retrofit2.http.GET
import retrofit2.http.Path

interface BridgesApiService {
    @GET("bridges")
    suspend fun getBridges(): List<NetworkBridge>

    @GET("bridges/{id}")
    suspend fun getBridgeById(@Path("id") id: Int): NetworkBridge
}