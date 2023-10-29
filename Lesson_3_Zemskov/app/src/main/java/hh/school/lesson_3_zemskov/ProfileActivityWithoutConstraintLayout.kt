package hh.school.lesson_3_zemskov

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hh.school.lesson_3_zemskov.databinding.ActivityProfileWithoutConstraintLayoutBinding
import hh.school.lesson_3_zemskov.model.User

class ProfileActivityWithoutConstraintLayout : AppCompatActivity() {

    private lateinit var binding: ActivityProfileWithoutConstraintLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileWithoutConstraintLayoutBinding.inflate(layoutInflater)
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
            val iconEditRegion = listItemRegion.stubEditIcon.inflate()
            iconEditRegion.setOnClickListener {
                Toast.makeText(applicationContext, R.string.list_item_icon_edit_text, Toast.LENGTH_SHORT).show()
            }

            listItemExitAccount.textViewOverline.visibility = View.GONE
            listItemExitAccount.stubExitAccountIcon.inflate()
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