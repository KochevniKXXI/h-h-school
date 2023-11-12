package hh.school.lesson_6_zemskov

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.navigation.NavigationBarView
import hh.school.lesson_6_zemskov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnPictureClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigationTopLevel: NavigationBarView = binding.navigationTopLevel
        binding.toolbar.title =
            navigationTopLevel.menu.findItem(navigationTopLevel.selectedItemId).title

        navigationTopLevel.setOnItemSelectedListener { item ->
            binding.toolbar.title = item.title

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

    fun addMenu(
        @MenuRes menuRes: Int,
        owner: LifecycleOwner
    ) {
        binding.toolbar.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(menuRes, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    Toast.makeText(this@MainActivity, menuItem.title, Toast.LENGTH_SHORT).show()
                    return true
                }
            },
            owner
        )
    }

    override fun onPictureClick(caption: String?) {
        Toast.makeText(this, caption, Toast.LENGTH_SHORT).show()
    }
}