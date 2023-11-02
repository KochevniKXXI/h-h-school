package hh.school.lesson_4_zemskov

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hh.school.lesson_4_zemskov.databinding.ItemGridBinding
import hh.school.lesson_4_zemskov.databinding.ItemListBinding

const val TYPE_GRID = 0
const val TYPE_LIST = 1

class ItemsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<InfoItem>()

    lateinit var onItemClick: (InfoItem, View) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_GRID -> GridInfoItemViewHolder(
                ItemGridBinding.inflate(LayoutInflater.from(parent.context)),
                onItemClick
            )

            TYPE_LIST -> ListInfoItemViewHolder(
                ItemListBinding.inflate(LayoutInflater.from(parent.context)),
                onItemClick
            )

            else -> error("Unknown ViewType")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GridInfoItemViewHolder -> holder.bind(items[position] as InfoItem.DetailInfoItem)
            is ListInfoItemViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (
            position % 2 == 0 && position == items.indexOfLast { it is InfoItem.DetailInfoItem }
            || items[position] is InfoItem.BaseInfoItem
        ) {
            TYPE_LIST
        } else {
            TYPE_GRID
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(itemsInfo: List<InfoItem>) {
        items.clear()
        items.addAll(itemsInfo)
        notifyDataSetChanged()
    }
}