package hh.school.lesson_12_zemskov.ui.reminder_dialog_fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import hh.school.lesson_12_zemskov.BaseDialogFragment
import hh.school.lesson_12_zemskov.R
import hh.school.lesson_12_zemskov.databinding.ReminderPickerBinding

private const val PERIOD = 15

class ReminderDialogFragment : BaseDialogFragment() {

    private var _bindingContent: ReminderPickerBinding? = null
    private val bindingContent get() = _bindingContent!!
    private val args: ReminderDialogFragmentArgs by navArgs()
    private val viewModel: ReminderDialogViewModel by daggerViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _bindingContent = ReminderPickerBinding.inflate(layoutInflater)

        bindingContent.reminderPicker.apply {
            minValue = 0
            maxValue = 4
            wrapSelectorWheel = false
            setFormatter {
                when (it) {
                    0 -> getString(R.string.reminder_picker_value_off)
                    else -> getString(R.string.reminder_picker_value_on, it * PERIOD)
                }
            }
            (getChildAt(0) as EditText).filters = emptyArray()
        }
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.reminder_dialog_fragment_title, args.bridgeName))
            .setMessage(R.string.reminder_dialog_fragment_message)
            .setView(bindingContent.root)
            .setPositiveButton(R.string.positive_button_text) { _, _ ->
                updateReminder()
            }
            .setNegativeButton(R.string.negative_button_text) { _, _ ->
                dismiss()
            }
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingContent.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.reminder.observe(viewLifecycleOwner) {
            bindingContent.reminderPicker.value = it / PERIOD
        }
        if (savedInstanceState == null) {
            viewModel.loadSavedReminder(args.bridgeId)
        }
    }

    override fun onDestroyView() {
        _bindingContent = null
        super.onDestroyView()
    }

    private fun updateReminder() {
        with(bindingContent.reminderPicker) {
            if (value == 0) {
                viewModel.removeReminder(args.bridgeId)
            } else {
                viewModel.saveReminder(
                    args.bridgeId,
                    bindingContent.reminderPicker.value * PERIOD
                )
            }
        }
    }
}