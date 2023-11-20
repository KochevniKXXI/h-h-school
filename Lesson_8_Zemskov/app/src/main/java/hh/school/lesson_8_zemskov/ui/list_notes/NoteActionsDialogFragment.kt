package hh.school.lesson_8_zemskov.ui.list_notes

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.shape.MaterialShapeDrawable
import hh.school.lesson_8_zemskov.R

private const val ARG_NOTE_ID = "arg_note_id"

class NoteActionsDialogFragment : DialogFragment() {

    private val noteId by lazy { arguments?.getString(ARG_NOTE_ID) ?: "" }

    companion object {
        @JvmStatic
        fun newInstance(noteId: String) = NoteActionsDialogFragment().apply {
            arguments = bundleOf(
                ARG_NOTE_ID to noteId
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = DialogInterface.OnClickListener { dialogInterface, which ->
            (parentFragment as OnCompletionListener).onCompletion(dialogInterface, which, noteId)
        }
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.note_actions_dialog_title))
            .setPositiveButton(getString(R.string.button_delete_text), listener)
            .setNegativeButton(getString(R.string.button_archive_text), listener)
            .setNeutralButton(getString(R.string.button_cancel_text), listener)
            .apply {
                (background as MaterialShapeDrawable).setCornerSize(
                    resources.getDimension(R.dimen.colorize_dialog_corner_size)
                )
            }
            .create()
    }
}