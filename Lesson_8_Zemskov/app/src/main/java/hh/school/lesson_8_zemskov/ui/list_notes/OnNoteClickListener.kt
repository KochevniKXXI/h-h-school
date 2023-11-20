package hh.school.lesson_8_zemskov.ui.list_notes

interface OnNoteClickListener {
    fun onClick(noteId: String)
    fun onLongClick(noteId: String): Boolean
}