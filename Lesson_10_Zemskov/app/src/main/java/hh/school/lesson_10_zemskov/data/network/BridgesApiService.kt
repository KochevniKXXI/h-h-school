package hh.school.lesson_10_zemskov.data.network

import hh.school.lesson_10_zemskov.data.network.model.NetworkBridge
import retrofit2.http.GET
import retrofit2.http.Path

interface BridgesApiService {

    @GET("bridges")
    suspend fun getBridgesPoints(): List<NetworkBridge>

    @GET("bridges/{id}")
    suspend fun getBridgeInfoById(@Path("id") id: Int): NetworkBridge
}