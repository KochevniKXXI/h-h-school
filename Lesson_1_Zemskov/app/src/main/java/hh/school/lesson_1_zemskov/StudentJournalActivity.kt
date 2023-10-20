package hh.school.lesson_1_zemskov

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import hh.school.lesson_1_zemskov.databinding.ActivityStudentJournalBinding
import hh.school.lesson_1_zemskov.extensions.removeExtraSpaces
import hh.school.lesson_1_zemskov.extensions.toStringForDisplay

class StudentJournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentJournalBinding
    private val studentsSortedSet = sortedSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextStudentName.addTextChangedListener {
            binding.buttonSave.isEnabled = !it.isNullOrBlank()
        }

        binding.buttonSave.setOnClickListener {
            studentsSortedSet.addAll(
                binding.editTextStudentName.text.toString().removeExtraSpaces().split(" ")
            )
            binding.editTextStudentName.setText("")
        }

        binding.buttonShowList.setOnClickListener {
            binding.textViewStudentsList.text =
                if (studentsSortedSet.isEmpty()) {
                    resources.getString(R.string.empty_students_list)
                } else {
                    studentsSortedSet.map { "${it.trim()}\n" }.toStringForDisplay()
                }
        }
    }
}