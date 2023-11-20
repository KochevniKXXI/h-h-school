package hh.school.lesson_8_zemskov.data

import android.content.Context
import hh.school.lesson_8_zemskov.data.database.NotesDatabase
import hh.school.lesson_8_zemskov.data.repository.OfflineNotesRepository

class ApplicationContainer(private val context: Context) {
    val notesRepository by lazy {
        OfflineNotesRepository(NotesDatabase.getInstance(context).noteDao())
    }
}