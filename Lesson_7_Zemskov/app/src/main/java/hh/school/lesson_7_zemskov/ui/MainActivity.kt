package hh.school.lesson_7_zemskov.ui

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import hh.school.lesson_7_zemskov.R
import hh.school.lesson_7_zemskov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                resources.getColor(R.color.black_alpha_8),
                resources.getColor(R.color.black_alpha_8)
            ),
            navigationBarStyle = SystemBarStyle.light(
                resources.getColor(R.color.black),
                resources.getColor(R.color.black)
            )
        )
        binding.root.setOnApplyWindowInsetsListener { view, windowInsets ->
            view.updatePadding(
                bottom = windowInsets.systemWindowInsetBottom,
                left = windowInsets.systemWindowInsetLeft,
                right = windowInsets.systemWindowInsetRight
            )
            windowInsets
        }
        setContentView(binding.root)
    }
}