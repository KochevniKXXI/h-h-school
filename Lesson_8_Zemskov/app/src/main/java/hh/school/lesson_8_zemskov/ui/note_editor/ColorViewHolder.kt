package hh.school.lesson_8_zemskov.ui.note_editor

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import hh.school.lesson_8_zemskov.R
import hh.school.lesson_8_zemskov.databinding.ItemColorBinding

class ColorViewHolder(
    parent: ViewGroup,
    private val currentColor: Int,
    private val onColorClick: (Int) -> Unit,
    private val binding: ItemColorBinding = ItemColorBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) : ViewHolder(
    binding.root
) {
    fun bind(color: Int) = with(binding) {
        root.background.setTint(color)
        if (currentColor == color) {
            root.icon = root.resources.getDrawable(R.drawable.ic_done)
        }

        if (color == Color.WHITE) {
            root.strokeWidth = root.resources.getDimensionPixelSize(R.dimen.item_color_stroke_width)
            root.iconTint = ColorStateList.valueOf(root.resources.getColor(R.color.black))
        }

        root.setOnClickListener {
            onColorClick(color)
        }
    }
}