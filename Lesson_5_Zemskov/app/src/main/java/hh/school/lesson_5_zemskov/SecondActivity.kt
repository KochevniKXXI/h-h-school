package hh.school.lesson_5_zemskov

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hh.school.lesson_5_zemskov.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    companion object {
        fun start(context: Context) = context.startActivity(
            Intent(context, SecondActivity::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGoToThirdActivity.setOnClickListener {
            ThirdActivity.start(this)
        }
    }
}