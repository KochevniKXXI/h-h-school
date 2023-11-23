package hh.school.lesson_8_zemskov.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hh.school.lesson_8_zemskov.data.database.dao.NoteDao
import hh.school.lesson_8_zemskov.data.database.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = NotesDatabase.VERSION
)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val NAME = "notes_db"
        const val VERSION = 1

        @Volatile
        private var instance: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, NotesDatabase::class.java, NAME)
                    .build()
                    .also { instance = it }
            }
        }
    }
}