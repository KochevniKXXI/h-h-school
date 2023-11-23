package hh.school.lesson_7_zemskov.ui

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import hh.school.lesson_7_zemskov.R
import hh.school.lesson_7_zemskov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Navigator {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                ResourcesCompat.getColor(resources, R.color.black_alpha_8, this.theme),
                ResourcesCompat.getColor(resources, R.color.black_alpha_8, this.theme)
            ),
            navigationBarStyle = SystemBarStyle.light(
                ResourcesCompat.getColor(resources, R.color.black, this.theme),
                ResourcesCompat.getColor(resources, R.color.black, this.theme)
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

        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightNavigationBars = false
            isAppearanceLightStatusBars = false
        }

        setContentView(binding.root)
    }

    override fun startFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            add(R.id.fragmentContainerViewMain, fragment)
            addToBackStack(null)
        }
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }
}