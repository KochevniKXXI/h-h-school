package hh.school.lesson_8_zemskov.ui.list_notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import hh.school.lesson_8_zemskov.R
import hh.school.lesson_8_zemskov.databinding.ItemNoteBinding
import hh.school.lesson_8_zemskov.model.Note
import hh.school.lesson_8_zemskov.ui.list_notes.OnNoteClickListener

class NoteViewHolder(
    parent: ViewGroup,
    private val onNoteClickListener: OnNoteClickListener,
    private val binding: ItemNoteBinding = ItemNoteBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) : ViewHolder(
    binding.root
) {
    fun bind(note: Note) = with(binding) {
        note.title?.let { title ->
            binding.textViewTitle.apply {
                text = title
                setTextColor(
                    if (note.color == resources.getColor(R.color.white)) {
                        resources.getColor(R.color.black)
                    } else {
                        resources.getColor(R.color.white)
                    }
                )
                visibility = View.VISIBLE
            }
        } ?: {
            binding.textViewTitle.visibility = View.GONE
        }

        note.content?.let { content ->
            binding.textViewContent.apply {
                text = content
                setTextColor(
                    if (note.color == resources.getColor(R.color.white)) {
                        resources.getColor(R.color.grey_700)
                    } else {
                        resources.getColor(R.color.white)
                    }
                )
                visibility = View.VISIBLE
            }
        } ?: {
            binding.textViewContent.visibility = View.GONE
        }

        root.setCardBackgroundColor(note.color)

        root.setOnClickListener {
            onNoteClickListener.onClick(note.id)
        }

        root.setOnLongClickListener {
            onNoteClickListener.onLongClick(note.id)
        }
    }
}