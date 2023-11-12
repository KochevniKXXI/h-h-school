package hh.school.lesson_6_zemskov.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hh.school.lesson_6_zemskov.databinding.ItemServiceBinding
import hh.school.lesson_6_zemskov.model.Service

class ServicesAdapter : RecyclerView.Adapter<ServiceViewHolder>() {

    private val listItems = mutableListOf<Service>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder(
            ItemServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = listItems.size

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    fun setList(listServices: List<Service>) {
        listItems.clear()
        listItems.addAll(listServices)
        notifyDataSetChanged()
    }
}