package hh.school.lesson_8_zemskov.data.repository

import hh.school.lesson_8_zemskov.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun archiveNote(id: String)
    suspend fun deleteNote(id: String)
    fun getNotArchivedNotesStream(): Flow<List<Note>>
    fun getNoteStream(id: String): Flow<Note>
}