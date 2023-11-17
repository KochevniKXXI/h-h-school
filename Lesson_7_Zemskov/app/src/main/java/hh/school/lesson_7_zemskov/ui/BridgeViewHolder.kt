package hh.school.lesson_7_zemskov.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hh.school.lesson_7_zemskov.R
import hh.school.lesson_7_zemskov.databinding.ItemBridgeBinding
import hh.school.lesson_7_zemskov.extensions.asString
import hh.school.lesson_7_zemskov.model.Bridge
import hh.school.lesson_7_zemskov.model.Divorce
import hh.school.lesson_7_zemskov.utils.Time
import java.util.Calendar

class BridgeViewHolder(
    parent: ViewGroup,
    private val onClick: (Int, List<Divorce>) -> Unit,
    private val binding: ItemBridgeBinding = ItemBridgeBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) : RecyclerView.ViewHolder(binding.root) {
    private val calendar = Calendar.getInstance()

    fun bind(bridge: Bridge) = with(binding) {
        textViewName.text = bridge.name
        textViewDivorces.text = bridge.divorces.asString()

        val currentTime = Time(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
        val divorces = bridge.divorces.map {
            try {
                Time.parse(it.start)..Time.parse(it.end)
            } catch (e: Exception) {
                null
            }
        }
        val imageResId = if (divorces.any { it != null && currentTime in it }) {
            R.drawable.ic_brige_late
        } else if (divorces.any { it != null && currentTime.until(it.start) <= Time(1, 0) }) {
            R.drawable.ic_brige_soon
        } else {
            R.drawable.ic_brige_normal
        }
        imageViewBridgeState.setImageResource(imageResId)

        root.setOnClickListener {
            onClick(bridge.id, bridge.divorces)
        }
    }
}