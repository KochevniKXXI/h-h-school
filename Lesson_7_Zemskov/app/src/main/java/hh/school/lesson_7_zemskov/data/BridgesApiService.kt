package hh.school.lesson_7_zemskov.data

import hh.school.lesson_7_zemskov.data.model.Bridge
import retrofit2.http.GET
import retrofit2.http.Path

interface BridgesApiService {

    @GET("bridges")
    suspend fun getBridges(): List<Bridge>

    @GET("bridges/{id}")
    suspend fun getBridge(@Path("id") id: Int): Bridge
}