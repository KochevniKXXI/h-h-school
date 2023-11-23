package hh.school.lesson_8_zemskov.ui.note_editor

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter

class ColorsAdapter(
    private val listColors: IntArray,
    private val currentColor: Int
) : Adapter<ColorViewHolder>() {

    lateinit var onColorClick: (Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(parent, currentColor, onColorClick)
    }

    override fun getItemCount() = listColors.size

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(listColors[position])
    }
}