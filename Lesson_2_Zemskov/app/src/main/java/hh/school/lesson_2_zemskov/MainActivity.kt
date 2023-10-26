package hh.school.lesson_2_zemskov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hh.school.lesson_2_zemskov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNavigateToExercise1.setOnClickListener {
            startActivity(Intent(this, ExerciseFirstActivity::class.java))
        }

        binding.buttonNavigateToExercise2.setOnClickListener {
            startActivity(Intent(this, ExerciseSecondActivity::class.java))
        }
    }
}