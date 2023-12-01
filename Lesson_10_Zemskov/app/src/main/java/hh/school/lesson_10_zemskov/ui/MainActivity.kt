package hh.school.lesson_10_zemskov.ui

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import dagger.hilt.android.AndroidEntryPoint
import hh.school.lesson_10_zemskov.R
import hh.school.lesson_10_zemskov.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                ResourcesCompat.getColor(resources, R.color.black_alpha_8, theme),
                ResourcesCompat.getColor(resources, R.color.black_alpha_8, theme)
            ),
            navigationBarStyle = SystemBarStyle.light(
                ResourcesCompat.getColor(resources, R.color.black, theme),
                ResourcesCompat.getColor(resources, R.color.black, theme)
            )
        )
        binding.root.setOnApplyWindowInsetsListener { view, windowInsets ->
            val windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(windowInsets)
            view.updatePadding(
                bottom = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom,
                right = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).right
            )
            windowInsets
        }

        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightNavigationBars = false
            isAppearanceLightStatusBars = false
        }

        setContentView(binding.root)
    }
}