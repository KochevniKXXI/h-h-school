package hh.school.lesson_8_zemskov.ui.list_notes

import android.content.DialogInterface

interface OnCompletionListener {
    fun onCompletion(dialogInterface: DialogInterface, which: Int, noteId: String)
}