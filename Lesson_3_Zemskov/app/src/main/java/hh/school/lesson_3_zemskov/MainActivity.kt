package hh.school.lesson_3_zemskov

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hh.school.lesson_3_zemskov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNavigateToActivityWithoutConstraintLayout.setOnClickListener {
            startActivity(Intent(this, ProfileActivityWithoutConstraintLayout::class.java))
        }
        binding.buttonNavigateToActivityOnConstraintLayout.setOnClickListener {
            startActivity(Intent(this, ProfileActivityOnConstraintLayout::class.java))
        }
    }
}