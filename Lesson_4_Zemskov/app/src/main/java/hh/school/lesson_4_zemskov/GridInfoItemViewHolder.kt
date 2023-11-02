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
    fun bind(detailInfoItem: InfoItem.DetailInfoItem) = with(binding) {
        root.setOnClickListener {
            onItemClick(detailInfoItem, it)
        }
        topIcon.setImageResource(detailInfoItem.iconResourceId)
        textViewHeadline.text = detailInfoItem.title

        val supportingTextColorResId = if (bindingAdapterPosition <= 1) R.color.red_500 else R.color.grey_800
        textViewSupportingText.text = detailInfoItem.description
        textViewSupportingText.setTextColor(binding.root.context.resources.getColor(supportingTextColorResId))
    }
}