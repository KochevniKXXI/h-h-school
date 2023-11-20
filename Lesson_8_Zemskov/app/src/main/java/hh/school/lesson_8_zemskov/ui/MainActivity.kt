package hh.school.lesson_8_zemskov.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import hh.school.lesson_8_zemskov.R
import hh.school.lesson_8_zemskov.databinding.ActivityMainBinding
import hh.school.lesson_8_zemskov.ui.note_editor.NoteEditorFragment

class MainActivity : AppCompatActivity(), Navigator {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun startNoteEditorFragment(noteId: String?) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainerView, NoteEditorFragment.newInstance(noteId))
            addToBackStack(null)
        }
    }
}