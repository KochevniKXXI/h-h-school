package hh.school.lesson_4_zemskov

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import hh.school.lesson_4_zemskov.databinding.ItemListBinding

class ListInfoItemViewHolder(
    private val binding: ItemListBinding,
    private val onItemClick: (InfoItem, View) -> Unit
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(infoItem: InfoItem) = with(binding) {
        root.setOnClickListener {
            onItemClick(infoItem, it)
        }
        leadingIcon.setImageResource(infoItem.iconResourceId)
        textViewHeadline.text = infoItem.title
        if (infoItem is InfoItem.DetailInfoItem) {
            textViewSupportingText.visibility = View.VISIBLE
            textViewSupportingText.text = infoItem.description
        } else {
            textViewSupportingText.visibility = View.GONE
            textViewSupportingText.text = ""
        }
    }
}