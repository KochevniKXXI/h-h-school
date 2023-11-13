package hh.school.lesson_5_zemskov

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import hh.school.lesson_5_zemskov.contracts.FifthActivityResultContract
import hh.school.lesson_5_zemskov.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private val launcher = registerForActivityResult(
        FifthActivityResultContract()
    ) { resultText ->
        resultText?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun start(context: Context) = context.startActivity(
            Intent(context, ThirdActivity::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGoToMainActivity.setOnClickListener {
            FirstActivity.start(this)
        }

        binding.buttonGoToFifthActivity.setOnClickListener {
            launcher.launch(Unit)
        }
    }
}