package hh.school.lesson_2_zemskov

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hh.school.lesson_2_zemskov.databinding.ActivityExerciseFirstBinding

class ExerciseFirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}