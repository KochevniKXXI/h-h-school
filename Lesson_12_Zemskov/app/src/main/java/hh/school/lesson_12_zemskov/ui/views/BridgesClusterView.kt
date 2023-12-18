package hh.school.lesson_12_zemskov.ui.views

import android.content.Context
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textview.MaterialTextView
import hh.school.lesson_12_zemskov.R
import hh.school.lesson_12_zemskov.ui.model.BridgeState

class BridgesClusterView(context: Context) : LinearLayout(context) {

    init {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        orientation = HORIZONTAL
        setBackgroundResource(R.drawable.view_cluster_background)
    }

    fun setData(bridgesStates: List<BridgeState>) {
        bridgesStates.associateWith { bridgeState ->
            bridgesStates.count { it == bridgeState }
        }.forEach {
            addView(MaterialTextView(context).apply {
                val iconSize = resources.getDimensionPixelSize(R.dimen.placemark_size)
                val icon = ResourcesCompat.getDrawable(resources, it.key.imageResId, context.theme)
                    ?.apply {
                        this.setBounds(0, 0, iconSize, iconSize)
                    }
                this.text = it.value.toString()
                this.textSize = resources.getDimension(R.dimen.cluster_text_size)
                this.setTextColor(ResourcesCompat.getColor(resources, R.color.black, context.theme))
                this.setCompoundDrawables(icon, null, null, null)
                this.compoundDrawablePadding =
                    resources.getDimensionPixelSize(R.dimen.cluster_drawable_padding)
            })
        }
    }
}