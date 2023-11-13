package hh.school.lesson_5_zemskov

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hh.school.lesson_5_zemskov.databinding.ActivityFourthBinding
import java.text.SimpleDateFormat
import java.util.Date

class FourthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFourthBinding

    companion object {
        const val KEY_CURRENT_TIME_MILLIS = "key_current_time_millis"

        fun start(context: Context) = context.startActivity(
            Intent(context, FourthActivity::class.java).apply {
                putExtra(KEY_CURRENT_TIME_MILLIS, System.currentTimeMillis())
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFourthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewCurrentDateTime.text = resources.getString(
            R.string.text_view_current_date_time_text,
            intent.getCurrentDateTimeAsString()
        )

        binding.buttonGoToFourthActivityAgain.setOnClickListener {
            start(this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        binding.textViewCurrentDateTime.text = resources.getString(
            R.string.text_view_current_date_time_text,
            intent?.getCurrentDateTimeAsString()
        )
    }
}

private fun Intent.getCurrentDateTimeAsString() = SimpleDateFormat.getDateTimeInstance().format(
    Date(this.getLongExtra(FourthActivity.KEY_CURRENT_TIME_MILLIS, 0))
)