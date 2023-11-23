package hh.school.lesson_8_zemskov.ui.list_notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
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
        if (note.title.isNotBlank()) {
            binding.textViewTitle.apply {
                text = note.title
                setTextColor(
                    if (note.color == ResourcesCompat.getColor(
                            resources,
                            R.color.white,
                            binding.root.context.theme
                        )
                    ) {
                        ResourcesCompat.getColor(
                            resources,
                            R.color.black,
                            binding.root.context.theme
                        )
                    } else {
                        ResourcesCompat.getColor(
                            resources,
                            R.color.white,
                            binding.root.context.theme
                        )
                    }
                )
                isVisible = true
            }
        } else {
            binding.textViewTitle.isVisible = false
        }

        if (note.content.isNotBlank()) {
            binding.textViewContent.apply {
                text = note.content
                setTextColor(
                    if (note.color == ResourcesCompat.getColor(
                            resources,
                            R.color.white,
                            binding.root.context.theme
                        )
                    ) {
                        ResourcesCompat.getColor(
                            resources,
                            R.color.grey_700,
                            binding.root.context.theme
                        )
                    } else {
                        ResourcesCompat.getColor(
                            resources,
                            R.color.white,
                            binding.root.context.theme
                        )
                    }
                )
                isVisible = true
            }
        } else {
            binding.textViewContent.isVisible = false
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