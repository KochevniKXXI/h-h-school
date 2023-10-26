package hh.school.lesson_2_zemskov

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hh.school.lesson_2_zemskov.databinding.ActivityExerciseSecondBinding

class ExerciseSecondActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityExerciseSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityExerciseSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}