package hh.school.lesson_8_zemskov.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import hh.school.lesson_8_zemskov.data.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Query("UPDATE ${NoteEntity.TABLE_NAME} SET archived = 1 WHERE id = :id")
    suspend fun archiveNote(id: String)

    @Query("DELETE FROM ${NoteEntity.TABLE_NAME} WHERE id = :id")
    suspend fun deleteNoteById(id: String)

    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME} WHERE archived = 0")
    fun getNotArchivedNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME} WHERE id = :id")
    fun getNote(id: String): Flow<NoteEntity>

    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME} " +
            "WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<NoteEntity>>
}