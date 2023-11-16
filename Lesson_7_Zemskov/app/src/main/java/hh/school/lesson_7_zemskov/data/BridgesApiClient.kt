package hh.school.lesson_7_zemskov.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://gdemost.handh.ru:1235/"

object BridgesApiClient {
    val apiService: BridgesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BridgesApiService::class.java)
    }
}