package hh.school.lesson_8_zemskov.model

import android.graphics.Color
import hh.school.lesson_8_zemskov.data.database.entity.NoteEntity
import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val content: String = "",
    val color: Int = Color.WHITE,
    val archived: Boolean = false
) {
    fun asEntity() = NoteEntity(
        id = id,
        title = title,
        content = content,
        color = color,
        archived = archived
    )
}
