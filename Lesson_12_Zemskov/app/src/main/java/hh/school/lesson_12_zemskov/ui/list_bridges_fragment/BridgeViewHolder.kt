package hh.school.lesson_12_zemskov.ui.list_bridges_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import hh.school.lesson_12_zemskov.databinding.ItemBridgeBinding
import hh.school.lesson_12_zemskov.ui.model.Bridge
import hh.school.lesson_12_zemskov.ui.model.Divorce

class BridgeViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Int, List<Divorce>) -> Unit,
    private val binding: ItemBridgeBinding = ItemBridgeBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) : ViewHolder(binding.root) {
    fun bind(bridge: Bridge) = with(binding) {
        root.bridge = bridge
        root.setOnClickListener {
            onItemClick(bridge.id, bridge.divorces)
        }
    }
}