package hh.school.lesson_5_zemskov

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import hh.school.lesson_5_zemskov.adapters.ServicesAdapter
import hh.school.lesson_5_zemskov.data.DataSource
import hh.school.lesson_5_zemskov.databinding.ActivitySixthBinding

private const val URL_APP_BAR_BACKGROUND = "https://img.freepik.com/free-photo/empty-floor-front-of-modern-building_1127-3154.jpg"

class SixthActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySixthBinding
    private val servicesAdapter = ServicesAdapter()

    companion object {
        fun start(context: Context) = context.startActivity(
            Intent(context, SixthActivity::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySixthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide
            .with(this)
            .load(URL_APP_BAR_BACKGROUND)
            .into(binding.imageViewAppBarBackground)

        binding.content.textButtonAllServices.setOnClickListener {
            Snackbar.make(it, R.string.all_services_text, Snackbar.LENGTH_SHORT).show()
        }

        binding.content.textViewOfferService.setOnClickListener {
            Snackbar.make(it, R.string.offer_service, Snackbar.LENGTH_SHORT).show()
        }

        with(binding.content.recyclerViewServices) {
            addItemDecoration(
                DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL).apply {
                    setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.horizontal_divider, theme)!!)
                }
            )
            adapter = servicesAdapter
        }
        servicesAdapter.setList(DataSource.listServices)
    }
}