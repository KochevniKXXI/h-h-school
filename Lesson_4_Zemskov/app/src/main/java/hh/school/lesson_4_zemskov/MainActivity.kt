package hh.school.lesson_4_zemskov

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.google.android.material.snackbar.Snackbar
import hh.school.lesson_4_zemskov.data.DataSource
import hh.school.lesson_4_zemskov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val itemsAdapter = ItemsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_info -> {
                    AlertDialog.Builder(this).apply {
                        setTitle(R.string.menu_actions_info_title)
                        setCancelable(false)
                        setPositiveButton(R.string.button_positive_text) { dialog, _ ->
                            dialog.dismiss()
                        }
                    }.create().show()
                }

                R.id.item_home -> Toast.makeText(
                    this,
                    R.string.manu_actions_in_out_title,
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }

        with(binding.recyclerViewInfo) {
            addItemDecoration(
                SpaceEvenlyItemDecoration(R.dimen.recycler_view_space_evenly)
            )
            (layoutManager as GridLayoutManager).spanSizeLookup =
                object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (itemsAdapter.getItemViewType(position) == TYPE_GRID) 1 else 2
                    }
                }
            adapter = itemsAdapter.apply {
                onItemClick = { infoItem, view ->
                    Snackbar.make(view, infoItem.title, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        itemsAdapter.setList(DataSource.listInfoItems())
    }
}