package hh.school.lesson_8_zemskov

import android.app.Application
import hh.school.lesson_8_zemskov.data.ApplicationContainer

class NotesApplication : Application() {
    lateinit var container: ApplicationContainer

    override fun onCreate() {
        super.onCreate()
        container = ApplicationContainer(this)
    }
}