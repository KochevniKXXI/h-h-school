package hh.school.lesson_1_zemskov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import hh.school.lesson_1_zemskov.databinding.ActivityStudentInfoBinding
import hh.school.lesson_1_zemskov.extensions.removeExtraSpaces
import hh.school.lesson_1_zemskov.extensions.toStringForDisplay
import hh.school.lesson_1_zemskov.model.Student
import java.util.UUID

class StudentInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentInfoBinding
    private val studentsInfoMap = hashMapOf<UUID, Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textInputEditTextStudentInfo.apply {
            setOnKeyListener { view, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    val id = UUID.randomUUID()
                    val info = (view as TextInputEditText).text.toString()
                    try {
                        studentsInfoMap[id] = info.toStudent(id)
                        view.setText("")
                    } catch (e: IllegalArgumentException) {
                        binding.textInputLayoutStudentInfo.error = e.message
                    }
                    return@setOnKeyListener true
                }
                false
            }
            addTextChangedListener {
                binding.textInputLayoutStudentInfo.isErrorEnabled = false
            }
        }

        binding.buttonShowInfo.setOnClickListener {
            binding.textViewStudentsInfo.text =
                if (studentsInfoMap.isEmpty()) {
                    resources.getString(R.string.empty_students_list)
                } else {
                    studentsInfoMap.map { "${it.value}\n\n" }.toStringForDisplay()
                }
        }
    }
}

private fun String.toStudent(id: UUID): Student {
    val tempArray = removeExtraSpaces().split(" ")
    return if (tempArray.size == 4) {
        Student(
            id = id,
            name = tempArray[0],
            surname = tempArray[1],
            grade = tempArray[2],
            birthdayYear = tempArray[3].toUShort()
        )
    } else throw IllegalArgumentException("Неверный формат данных\nПример: Иван Иванов 1А 2000")
}