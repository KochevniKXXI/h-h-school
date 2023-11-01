package hh.school.lesson_4_zemskov

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import hh.school.lesson_4_zemskov.databinding.ItemGridBinding

class GridInfoItemViewHolder(
    private val binding: ItemGridBinding,
    private val onItemClick: (InfoItem, View) -> Unit
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(detailInfoItem: InfoItem.DetailInfoItem, position: Int) = with(binding) {
        root.setOnClickListener {
            onItemClick(detailInfoItem, it)
        }
        topIcon.setImageResource(detailInfoItem.iconResourceId)
        textViewHeadline.text = detailInfoItem.title
        textViewSupportingText.text = detailInfoItem.description
        textViewSupportingText.setTextColor(
            if (position <= 1) binding.root.context.resources.getColor(R.color.red_500)
            else binding.root.context.resources.getColor(R.color.grey_600)
        )
    }
}