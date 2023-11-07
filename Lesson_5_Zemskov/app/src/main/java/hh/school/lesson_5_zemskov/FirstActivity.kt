package hh.school.lesson_5_zemskov

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hh.school.lesson_5_zemskov.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding

    companion object {
        fun start(context: Context) = context.startActivity(
            Intent(context, FirstActivity::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGoToSecondActivity.setOnClickListener {
            SecondActivity.start(this)
        }

        binding.buttonGoToFourthActivity.setOnClickListener {
            FourthActivity.start(this)
        }

        binding.buttonGoToSixthActivity.setOnClickListener {
            SixthActivity.start(this)
        }
    }
}