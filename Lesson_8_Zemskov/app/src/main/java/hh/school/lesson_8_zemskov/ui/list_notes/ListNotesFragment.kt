package hh.school.lesson_8_zemskov.ui.list_notes

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import hh.school.lesson_8_zemskov.NotesApplication
import hh.school.lesson_8_zemskov.R
import hh.school.lesson_8_zemskov.databinding.FragmentListNotesBinding
import hh.school.lesson_8_zemskov.databinding.MergeInfoBinding
import hh.school.lesson_8_zemskov.ui.Navigator
import hh.school.lesson_8_zemskov.ui.SpaceEvenlyItemDecoration
import hh.school.lesson_8_zemskov.ui.list_notes.adapter.NotesAdapter
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ListNotesFragment : Fragment(), OnCompletionListener {

    private var _binding: FragmentListNotesBinding? = null
    private val binding get() = _binding!!
    private var _infoBinding: MergeInfoBinding? = null
    private val infoBinding get() = _infoBinding!!

    private val notesAdapter = NotesAdapter()
    private val notesRepository by lazy { (activity?.application as NotesApplication).container.notesRepository }

    companion object {
        @JvmStatic
        fun newInstance() = ListNotesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListNotesBinding.inflate(inflater, container, false)
        _infoBinding = MergeInfoBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiSettings()
        getNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _infoBinding = null
    }

    override fun onCompletion(dialogInterface: DialogInterface, which: Int, noteId: String) {
        lifecycleScope.launch {
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> notesRepository.deleteNote(noteId)
                DialogInterface.BUTTON_NEGATIVE -> notesRepository.archiveNote(noteId)
            }
            dialogInterface.dismiss()
        }
    }

    private fun setUiSettings() {
        binding.recyclerViewNotes.apply {
            addItemDecoration(
                SpaceEvenlyItemDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.item_note_divider_size
                    )
                )
            )
            adapter = notesAdapter.apply {
                onNoteClickListener = object : OnNoteClickListener {
                    override fun onClick(noteId: String) {
                        (activity as? Navigator)?.startNoteEditorFragment(noteId)
                    }

                    override fun onLongClick(noteId: String): Boolean {
                        NoteActionsDialogFragment
                            .newInstance(noteId)
                            .show(childFragmentManager, null)
                        return true
                    }
                }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            (activity as? Navigator)?.startNoteEditorFragment()
        }

        infoBinding.circularProgressIndicator.setVisibilityAfterHide(View.GONE)

        binding.toolbar.menu.findItem(R.id.itemSearch).apply {
            setOnActionExpandListener(object : OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                    searchNotes((actionView as SearchView).query.toString())
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                    getNotes()
                    return true
                }
            })

            (actionView as SearchView).apply {
                setOnQueryTextListener(object : OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        searchNotes(newText)
                        return true
                    }
                })
            }
        }
    }

    private fun searchNotes(newText: String?) {
        lifecycleScope.launch {
            newText?.let {
                notesRepository.searchNotes(newText)
                    .onStart {
                        infoBinding.circularProgressIndicator.show()
                    }
                    .collect {
                        infoBinding.circularProgressIndicator.hide()
                        if (it.isEmpty()) {
                            infoBinding.textViewMessage.text =
                                getString(R.string.message_empty_response)
                            infoBinding.textViewMessage.visibility = View.VISIBLE
                        } else {
                            infoBinding.textViewMessage.visibility = View.GONE
                        }
                        notesAdapter.setNotes(it)
                    }
            }
        }
    }

    private fun getNotes() {
        lifecycleScope.launch {
            notesRepository.getNotArchivedNotesStream()
                .onStart {
                    infoBinding.circularProgressIndicator.show()
                }
                .collect {
                    infoBinding.circularProgressIndicator.hide()
                    if (it.isEmpty()) {
                        infoBinding.textViewMessage.text =
                            getString(R.string.message_empty_list_notes)
                        infoBinding.textViewMessage.visibility = View.VISIBLE
                    } else {
                        infoBinding.textViewMessage.visibility = View.GONE
                    }
                    notesAdapter.setNotes(it)
                }
        }
    }
}