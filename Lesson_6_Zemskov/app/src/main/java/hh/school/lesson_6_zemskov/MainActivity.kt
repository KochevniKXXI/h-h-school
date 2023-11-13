package hh.school.lesson_6_zemskov

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationBarView
import hh.school.lesson_6_zemskov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnPictureClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigationTopLevel = binding.navigationTopLevel as NavigationBarView

        navigationTopLevel.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemOne -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<OneItemFragment>(R.id.fragmentContainerViewContent)
                    }
                    true
                }

                R.id.itemTwo -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<TwoItemFragment>(R.id.fragmentContainerViewContent)
                    }
                    true
                }

                R.id.itemThree -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<ThreeItemFragment>(R.id.fragmentContainerViewContent)
                    }
                    true
                }

                else -> false
            }
        }
    }

    override fun onPictureClick(caption: String?) {
        Toast.makeText(this, caption, Toast.LENGTH_SHORT).show()
    }
}