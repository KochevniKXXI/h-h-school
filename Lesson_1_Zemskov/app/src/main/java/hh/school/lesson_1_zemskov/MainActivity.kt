package hh.school.lesson_1_zemskov

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hh.school.lesson_1_zemskov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.buttonNavigateToStudentJournalActivity.setOnClickListener {
            startActivity(Intent(this, StudentJournalActivity::class.java))
        }

        binding.buttonNavigateToStudentInfoActivity.setOnClickListener {
            startActivity(Intent(this, StudentInfoActivity::class.java))
        }
    }
}