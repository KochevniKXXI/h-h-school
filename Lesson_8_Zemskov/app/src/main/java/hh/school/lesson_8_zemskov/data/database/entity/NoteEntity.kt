package hh.school.lesson_8_zemskov.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import hh.school.lesson_8_zemskov.model.Note

@Entity(tableName = NoteEntity.TABLE_NAME)
data class NoteEntity(
    @PrimaryKey val id: String,
    val title: String?,
    val content: String?,
    val color: Int,
    val archived: Boolean
) {
    companion object {
        const val TABLE_NAME = "notes"
    }

    fun asInternalModel() = Note(
        id = id,
        title = title,
        content = content,
        color = color,
        archived = archived
    )
}
