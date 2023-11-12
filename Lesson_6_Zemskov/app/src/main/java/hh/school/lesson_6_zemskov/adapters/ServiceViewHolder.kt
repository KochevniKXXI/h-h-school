package hh.school.lesson_6_zemskov.adapters

import android.text.Html
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hh.school.lesson_6_zemskov.R
import hh.school.lesson_6_zemskov.databinding.ItemServiceBinding
import hh.school.lesson_6_zemskov.model.Service

class ServiceViewHolder(
    private val binding: ItemServiceBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(serviceItem: Service) = with(binding) {
        imageViewIcon.setImageResource(serviceItem.iconResId)
        textViewName.text = serviceItem.name
        textViewSerialNumber.text = serviceItem.serialNumber

        with(textViewAdditionalInfo) {
            if (serviceItem.metersDataDate.isNotBlank()) {
                val addInfo = resources.getString(
                    R.string.helper_message,
                    serviceItem.metersDataDate,
                    serviceItem.nextCalculationDate
                )
                text = Html.fromHtml(addInfo)
                setTextColor(resources.getColor(R.color.grey_blue_800))
                setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
            } else {
                text = resources.getString(R.string.alert_message, serviceItem.nextCalculationDate)
                setTextColor(resources.getColor(R.color.red_500))
                setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_alert, 0, 0, 0)
            }
        }

        when (serviceItem) {
            is Service.SingleMeterService -> {
                textInputLayoutFirstMeter.hint = binding.root.resources.getString(R.string.item_service_one_edit_text_hint)
                textInputLayoutSecondMeter.visibility = View.GONE
                textInputLayoutThirdMeter.visibility = View.GONE
            }
            is Service.DayNightMaxService -> {
                textInputLayoutFirstMeter.hint = binding.root.resources.getString(R.string.item_service_first_edit_text_hint)
                textInputLayoutSecondMeter.visibility = View.VISIBLE
                textInputLayoutThirdMeter.visibility = View.VISIBLE
            }
        }

        root.setOnClickListener {
            Snackbar.make(it, serviceItem.name, Snackbar.LENGTH_SHORT).show()
        }
        imageViewMore.setOnClickListener {
            Snackbar.make(it, imageViewMore.contentDescription, Snackbar.LENGTH_SHORT).show()
        }
        imageViewInfo.setOnClickListener {
            Snackbar.make(it, imageViewInfo.contentDescription, Snackbar.LENGTH_SHORT).show()
        }
        buttonSend.setOnClickListener {
            Snackbar.make(it, buttonSend.contentDescription, Snackbar.LENGTH_SHORT).show()
        }
    }
}