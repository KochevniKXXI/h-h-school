package hh.school.lesson_11_zemskov

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hh.school.lesson_11_zemskov.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

        binding.barChartView.data = generateSequence(startDate) { calendar ->
            calendar.apply { add(Calendar.DAY_OF_MONTH, 1) }.takeIf { it <= Calendar.getInstance() }
        }.associate {
            dateFormatter.format(it.time) to (20..70).random()
        }.toList()

        binding.barChartView.setOnClickListener {
            binding.barChartView.startAnimation()
        }
    }
}