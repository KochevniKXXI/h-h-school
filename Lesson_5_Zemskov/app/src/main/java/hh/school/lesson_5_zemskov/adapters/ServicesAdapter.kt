package hh.school.lesson_5_zemskov.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hh.school.lesson_5_zemskov.databinding.ItemServiceBinding
import hh.school.lesson_5_zemskov.model.ServiceItem

class ServicesAdapter : RecyclerView.Adapter<ServiceViewHolder>() {
    private val items = mutableListOf<ServiceItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder(
            ItemServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setList(itemsService: List<ServiceItem>) {
        items.clear()
        items.addAll(itemsService)
        notifyDataSetChanged()
    }
}