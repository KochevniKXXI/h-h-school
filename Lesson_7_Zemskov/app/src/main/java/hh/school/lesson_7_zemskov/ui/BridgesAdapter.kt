package hh.school.lesson_7_zemskov.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hh.school.lesson_7_zemskov.model.Bridge
import hh.school.lesson_7_zemskov.model.Divorce

class BridgesAdapter : RecyclerView.Adapter<BridgeViewHolder>() {

    private val bridges = mutableListOf<Bridge>()
    lateinit var onClick: (Int, List<Divorce>) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BridgeViewHolder {
        return BridgeViewHolder(parent, onClick)
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