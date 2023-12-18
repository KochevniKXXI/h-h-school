package hh.school.lesson_12_zemskov.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import hh.school.lesson_12_zemskov.data.network.BridgesApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "http://gdemost.handh.ru:1235/"

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideBridgesApiService(retrofit: Retrofit): BridgesApiService =
        retrofit.create(BridgesApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()
}