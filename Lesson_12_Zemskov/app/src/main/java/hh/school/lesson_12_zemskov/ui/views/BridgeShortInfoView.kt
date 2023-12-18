package hh.school.lesson_12_zemskov.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import hh.school.lesson_12_zemskov.R
import hh.school.lesson_12_zemskov.databinding.ViewBridgeShortInfoBinding
import hh.school.lesson_12_zemskov.ui.model.Bridge

class BridgeShortInfoView : MaterialCardView {

    var bridge: Bridge = Bridge()
        set(value) {
            field = value
            bind(field)
        }
    private val binding =
        ViewBridgeShortInfoBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BridgeShortInfoView, 0, 0)
        try {
            binding.iconButtonReminder.isVisible =
                typedArray.getBoolean(R.styleable.BridgeShortInfoView_showButtonReminder, false)
        } finally {
            typedArray.recycle()
        }
    }

    private fun bind(bridge: Bridge) = with(binding) {
        imageViewBridgeState.setImageResource(bridge.state.imageResId)
        textViewName.text = bridge.name
        textViewDivorces.text = bridge.getDivorcesAsString()
        iconButtonReminder.isChecked = bridge.reminder != 0
    }

    fun updateIconBridgeState() {
        binding.imageViewBridgeState.setImageResource(bridge.state.imageResId)
    }

    fun setOnReminderClickListener(l: OnClickListener) {
        binding.iconButtonReminder.setOnClickListener(l)
    }
}