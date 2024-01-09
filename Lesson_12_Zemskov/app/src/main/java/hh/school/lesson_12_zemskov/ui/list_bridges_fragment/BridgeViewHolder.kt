package hh.school.lesson_12_zemskov.ui.list_bridges_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import hh.school.lesson_12_zemskov.databinding.ItemBridgeBinding
import hh.school.lesson_12_zemskov.ui.model.Bridge

class BridgeViewHolder(
    parent: ViewGroup,
    private val clickListener: OnBridgeClickListener,
    private val binding: ItemBridgeBinding = ItemBridgeBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) : ViewHolder(binding.root) {
    fun bind(bridge: Bridge) = with(binding) {
        root.bridge = bridge
        root.setOnClickListener {
            clickListener.onItemClick(bridge.id, bridge.divorces)
        }
        root.setOnReminderClickListener {
            clickListener.onReminderClick(bridge.id, bridge.name)
        }
    }
}