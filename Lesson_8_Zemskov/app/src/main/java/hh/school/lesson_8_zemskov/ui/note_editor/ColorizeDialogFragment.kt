package hh.school.lesson_8_zemskov.ui.note_editor

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.shape.MaterialShapeDrawable
import hh.school.lesson_8_zemskov.R
import hh.school.lesson_8_zemskov.databinding.ColorChartBinding
import hh.school.lesson_8_zemskov.ui.SpaceBetweenItemDecoration

private const val ARG_COLOR = "arg_color"

class ColorizeDialogFragment : DialogFragment() {

    private val color by lazy { arguments?.getInt(ARG_COLOR) ?: Color.WHITE }

    companion object {
        @JvmStatic
        fun newInstance(color: Int) = ColorizeDialogFragment().apply {
            arguments = bundleOf(
                ARG_COLOR to color
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val colorChart = ColorChartBinding.inflate(layoutInflater).root.apply {
            addItemDecoration(
                SpaceBetweenItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.item_color_divider_size)
                )
            )
        }
        colorChart.adapter = ColorsAdapter(
            resources.getIntArray(R.array.note_background_colors),
            color
        ).apply {
            onColorClick = {
                (parentFragment as OnColorChangeListener).onColorChange(it)
                dialog?.dismiss()
            }
        }
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.colorize_dialog_title)
            .setView(colorChart)
            .setNegativeButton(R.string.button_cancel_text) { _, _ ->
                this.dismiss()
            }
            .apply {
                (background as MaterialShapeDrawable).setCornerSize(
                    resources.getDimension(R.dimen.colorize_dialog_corner_size)
                )
            }.create()
    }
}