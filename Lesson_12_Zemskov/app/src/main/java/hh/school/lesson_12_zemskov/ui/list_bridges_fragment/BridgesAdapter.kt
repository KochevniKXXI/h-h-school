package hh.school.lesson_12_zemskov.ui.list_bridges_fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import hh.school.lesson_12_zemskov.ui.model.Bridge
import hh.school.lesson_12_zemskov.ui.model.Divorce

class BridgesAdapter : Adapter<BridgeViewHolder>() {

    private val bridges = mutableListOf<Bridge>()
    lateinit var onItemClick: (Int, List<Divorce>) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BridgeViewHolder {
        return BridgeViewHolder(parent, onItemClick)
    }

    override fun getItemCount() = bridges.size

    override fun onBindViewHolder(holder: BridgeViewHolder, position: Int) {
        holder.bind(bridges[position])
    }

    fun submitBridges(bridges: List<Bridge>) {
        this.bridges.clear()
        this.bridges.addAll(bridges)
        notifyDataSetChanged()
    }
}