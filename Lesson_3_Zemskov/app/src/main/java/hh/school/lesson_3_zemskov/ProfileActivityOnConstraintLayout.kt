package hh.school.lesson_3_zemskov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import hh.school.lesson_3_zemskov.databinding.ActivityProfileOnConstraintLayoutBinding
import hh.school.lesson_3_zemskov.model.User

class ProfileActivityOnConstraintLayout : AppCompatActivity() {

    private lateinit var binding: ActivityProfileOnConstraintLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileOnConstraintLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        with(binding) {
            val personalDataLabels = resources.getStringArray(R.array.personal_data_labels)
            listItemName.textViewOverline.text = personalDataLabels[0]
            listItemSurname.textViewOverline.text = personalDataLabels[1]
            listItemEmail.textViewOverline.text = personalDataLabels[2]
            listItemLogin.textViewOverline.text = personalDataLabels[3]

            listItemRegion.textViewOverline.text = personalDataLabels[4]
            listItemRegion.imageViewEdit.visibility = View.VISIBLE
            listItemRegion.imageViewEdit.setOnClickListener {
                Toast.makeText(applicationContext, R.string.list_item_icon_edit_text, Toast.LENGTH_SHORT).show()
            }

            listItemExitAccount.textViewOverline.visibility = View.GONE
            listItemExitAccount.imageViewExitAccount.visibility = View.VISIBLE
            listItemExitAccount.textViewHeadline.text = resources.getString(R.string.exit_account_text)
            buttonExitAccount.setOnClickListener {
                Toast.makeText(applicationContext, R.string.exit_account_text, Toast.LENGTH_SHORT).show()
            }

            val user = User()
            textViewHeader.root.text = resources.getString(R.string.header_text, user.cardNumber, user.skill)
            listItemName.textViewHeadline.text = user.name
            listItemSurname.textViewHeadline.text = user.surname
            listItemEmail.textViewHeadline.text = user.email
            listItemLogin.textViewHeadline.text = user.login
            listItemRegion.textViewHeadline.text = user.region
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.actionEdit -> {
                Toast.makeText(applicationContext, R.string.menu_item_edit_text, Toast.LENGTH_SHORT)
                    .show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}