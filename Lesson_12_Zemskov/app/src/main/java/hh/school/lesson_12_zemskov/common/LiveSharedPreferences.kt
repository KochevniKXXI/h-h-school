package hh.school.lesson_12_zemskov.common

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

class LiveSharedPreferences(
    private val preferences: SharedPreferences
) : LiveData<Pair<String, Int>>() {
    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            key?.let {
                value = it to sharedPreferences.getInt(it, 0)
            }
        }

    override fun onActive() {
        super.onActive()
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onInactive() {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
        super.onInactive()
    }
}