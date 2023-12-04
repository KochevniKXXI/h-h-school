package hh.school.lesson_10_zemskov

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BridgeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(getString(R.string.mapkit_api_key))
    }
}