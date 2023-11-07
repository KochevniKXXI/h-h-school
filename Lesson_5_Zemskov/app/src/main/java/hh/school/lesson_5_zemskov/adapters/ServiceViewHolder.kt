package hh.school.lesson_5_zemskov.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import hh.school.lesson_5_zemskov.databinding.ItemServiceBinding
import hh.school.lesson_5_zemskov.model.ServiceItem

class ServiceViewHolder(
    private val binding: ItemServiceBinding
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(serviceItem: ServiceItem) = with(binding) {
        root.setOnClickListener {
            Snackbar.make(it, serviceItem.promotion, Snackbar.LENGTH_SHORT).show()
        }

        imageViewMore.setOnClickListener {
            Snackbar.make(it, "Другие акции ${serviceItem.title}", Snackbar.LENGTH_SHORT).show()
        }

        textViewTitle.text = serviceItem.title
        textViewPromotion.text = serviceItem.promotion
        textViewAddress.text = serviceItem.address

        Glide
            .with(binding.root.context)
            .load(serviceItem.imageUrl)
            .into(imageViewPoster)
    }
}