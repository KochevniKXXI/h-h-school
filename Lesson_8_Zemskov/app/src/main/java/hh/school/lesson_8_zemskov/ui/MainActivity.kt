package hh.school.lesson_8_zemskov.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import hh.school.lesson_8_zemskov.R
import hh.school.lesson_8_zemskov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Navigator {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun startFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainerView, fragment)
            addToBackStack(null)
        }
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }
}