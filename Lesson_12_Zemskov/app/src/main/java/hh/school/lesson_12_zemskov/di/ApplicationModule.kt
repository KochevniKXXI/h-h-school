package hh.school.lesson_12_zemskov.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import hh.school.lesson_12_zemskov.R
import javax.inject.Singleton

@Module
object ApplicationModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(
            context.resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
}