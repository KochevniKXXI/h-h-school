package hh.school.lesson_12_zemskov.ui

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import hh.school.lesson_12_zemskov.R
import hh.school.lesson_12_zemskov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initEdgeToEdge()
        setContentView(binding.root)
    }

    /**
     * Инициализирует и настраивает режим «От края до края» для всей [MainActivity].
     *
     */
    private fun initEdgeToEdge() {
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
                right = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).right,
                left = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).left
            )
            windowInsets
        }

        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightNavigationBars = false
            isAppearanceLightStatusBars = false
        }
    }
}