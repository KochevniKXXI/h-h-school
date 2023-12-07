package hh.school.lesson_11_zemskov

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import hh.school.lesson_11_zemskov.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(2_000)
            binding.barChartView.data = generateDateInterval()
            delay(2_000)
            binding.barChartView.data = generateDateInterval()
        }

        binding.barChartView.setOnClickListener {
            binding.barChartView.startAnimation()
        }
    }

    private fun generateDateInterval(): List<Pair<String, Int>> {
        val dateFormatter = SimpleDateFormat("dd.MM", Locale.getDefault())
        val startDate = (Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, -9)
        }.timeInMillis..System.currentTimeMillis()).random()
            .let {
                Calendar.getInstance().apply {
                    timeInMillis = it
                    clear(Calendar.AM_PM)
                    clear(Calendar.HOUR)
                    clear(Calendar.HOUR_OF_DAY)
                    clear(Calendar.MINUTE)
                    clear(Calendar.SECOND)
                    clear(Calendar.MILLISECOND)
                }
            }
        return generateSequence(startDate.clone() as Calendar) { calendar ->
            calendar.apply { add(Calendar.DAY_OF_MONTH, 1) }.takeIf { it <= Calendar.getInstance() }
        }.associate {
            dateFormatter.format(it.time) to (20..70).random()
        }.toList()
    }
}