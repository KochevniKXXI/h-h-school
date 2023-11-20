package hh.school.lesson_8_zemskov.ui.list_notes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import hh.school.lesson_8_zemskov.model.Note
import hh.school.lesson_8_zemskov.ui.list_notes.OnNoteClickListener

class NotesAdapter : Adapter<NoteViewHolder>() {

    private val listNotes = mutableListOf<Note>()
    lateinit var onNoteClickListener: OnNoteClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent, onNoteClickListener)
    }

    override fun getItemCount() = listNotes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    fun setNotes(notes: List<Note>) {
        listNotes.clear()
        listNotes.addAll(notes)
        notifyDataSetChanged()
    }
}