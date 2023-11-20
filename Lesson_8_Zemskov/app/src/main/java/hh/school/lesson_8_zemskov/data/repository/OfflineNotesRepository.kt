package hh.school.lesson_8_zemskov.data.repository

import hh.school.lesson_8_zemskov.data.database.dao.NoteDao
import hh.school.lesson_8_zemskov.data.database.entity.NoteEntity
import hh.school.lesson_8_zemskov.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineNotesRepository(private val noteDao: NoteDao) : NotesRepository {
    override suspend fun insertNote(note: Note) = noteDao.insertNote(note.asEntity())

    override suspend fun updateNote(note: Note) = noteDao.updateNote(note.asEntity())

    override suspend fun archiveNote(id: String) = noteDao.archiveNote(id)

    override suspend fun deleteNote(id: String) = noteDao.deleteNoteById(id)

    override fun getNotArchivedNotesStream(): Flow<List<Note>> =
        noteDao.getNotArchivedNotes().map { it.map(NoteEntity::asInternalModel) }

    override fun getNoteStream(id: String): Flow<Note> =
        noteDao.getNote(id).map { it.asInternalModel() }

    override fun searchNotes(query: String): Flow<List<Note>> =
        noteDao.searchNotes(query).map { it.map(NoteEntity::asInternalModel) }
}