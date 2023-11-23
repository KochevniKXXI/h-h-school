package hh.school.lesson_8_zemskov.ui.note_editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import hh.school.lesson_8_zemskov.NotesApplication
import hh.school.lesson_8_zemskov.R
import hh.school.lesson_8_zemskov.databinding.FragmentNoteEditorBinding
import hh.school.lesson_8_zemskov.databinding.MergeInfoBinding
import hh.school.lesson_8_zemskov.model.Note
import hh.school.lesson_8_zemskov.ui.Navigator
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

private const val ARG_NOTE_ID = "arg_note_id"

class NoteEditorFragment : Fragment(), OnColorChangeListener {

    private var _binding: FragmentNoteEditorBinding? = null
    private val binding get() = _binding!!
    private var _infoBinding: MergeInfoBinding? = null
    private val infoBinding get() = _infoBinding!!

    private val noteId by lazy { arguments?.getString(ARG_NOTE_ID) }
    private lateinit var note: Note

    private val notesRepository by lazy { (activity?.application as NotesApplication).container.notesRepository }

    companion object {
        @JvmStatic
        fun newInstance(noteId: String? = null) = NoteEditorFragment().apply {
            arguments = bundleOf(
                ARG_NOTE_ID to noteId
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteEditorBinding.inflate(inflater, container, false)
        _infoBinding = MergeInfoBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        infoBinding.circularProgressIndicator.setVisibilityAfterHide(View.GONE)

        binding.toolbar.setNavigationOnClickListener {
            lifecycleScope.launch {
                noteId?.let { updateNote() } ?: insertNewNote()
            }
            (activity as? Navigator)?.popBackStack()
        }

        val noteStream = getNoteStream()

        lifecycleScope.launch {
            noteStream.collect {
                note = it
                infoBinding.circularProgressIndicator.hide()
                infoBinding.textViewMessage.isVisible = false
                binding.toolbar.menu.children.forEach { menuItem -> menuItem.isVisible = true }
                binding.editTextTitle.setText(note.title)
                binding.editTextContent.setText(note.content)

                binding.toolbar.setOnMenuItemClickListener { itemMenu ->
                    when (itemMenu.itemId) {
                        R.id.itemColorize -> {
                            ColorizeDialogFragment.newInstance(note.color)
                                .show(childFragmentManager, null)
                        }
                    }
                    true
                }
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        _infoBinding = null
        super.onDestroyView()
    }

    override fun onColorChange(color: Int) {
        note = note.copy(color = color)
    }

    private fun getNoteStream() = noteId?.let {
        notesRepository.getNoteStream(it)
            .onStart {
                infoBinding.circularProgressIndicator.show()
            }
            .onEmpty {
                infoBinding.circularProgressIndicator.hide()
                infoBinding.textViewMessage.text = getString(R.string.message_note_deleted)
                infoBinding.textViewMessage.isVisible = true
                binding.editTextTitle.isVisible = false
                binding.editTextContent.isVisible = false
                binding.toolbar.setNavigationOnClickListener {
                    (activity as? Navigator)?.popBackStack()
                }
            }
    } ?: flowOf(Note())

    private suspend fun insertNewNote() {
        if (!binding.editTextTitle.text.isNullOrBlank() || !binding.editTextContent.text.isNullOrBlank()) {
            notesRepository.insertNote(
                note.copy(
                    title = binding.editTextTitle.text.toString(),
                    content = binding.editTextContent.text.toString()
                )
            )
        }
    }

    private suspend fun updateNote() {
        notesRepository.updateNote(
            note.copy(
                title = binding.editTextTitle.text.toString(),
                content = binding.editTextContent.text.toString()
            )
        )
    }
}