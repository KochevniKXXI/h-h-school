package hh.school.lesson_12_zemskov

import com.yandex.mapkit.MapKitFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import hh.school.lesson_12_zemskov.di.DaggerApplicationComponent

class BridgesApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.create()
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(getString(R.string.mapkit_api_key))
    }
}