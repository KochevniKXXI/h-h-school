package hh.school.lesson_3_zemskov

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hh.school.lesson_3_zemskov.databinding.ActivityProfileOnConstraintLayoutBinding
import hh.school.lesson_3_zemskov.model.User

class ProfileActivityOnConstraintLayout : AppCompatActivity() {

    private lateinit var binding: ActivityProfileOnConstraintLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileOnConstraintLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.toolbar.setOnMenuItemClickListener {
            Toast.makeText(applicationContext, R.string.menu_item_edit_text, Toast.LENGTH_SHORT)
                .show()
            true
        }

        with(binding) {
            listItemName.textViewOverline.text = resources.getString(R.string.label_name)
            listItemSurname.textViewOverline.text = resources.getString(R.string.label_surname)
            listItemEmail.textViewOverline.text = resources.getString(R.string.label_email)
            listItemLogin.textViewOverline.text = resources.getString(R.string.label_login)

            listItemRegion.textViewOverline.text = resources.getString(R.string.label_region)
            listItemRegion.imageViewEdit.visibility = View.VISIBLE
            listItemRegion.imageViewEdit.setOnClickListener {
                Toast.makeText(
                    applicationContext,
                    R.string.list_item_icon_edit_text,
                    Toast.LENGTH_SHORT
                ).show()
            }

            buttonExitAccount.root.setOnClickListener {
                Toast.makeText(applicationContext, R.string.exit_account_text, Toast.LENGTH_SHORT)
                    .show()
            }

            val user = User()
            textViewHeader.root.text =
                resources.getString(R.string.header_text, user.cardNumber, user.skill)
            listItemName.textViewHeadline.text = user.name
            listItemSurname.textViewHeadline.text = user.surname
            listItemEmail.textViewHeadline.text = user.email
            listItemLogin.textViewHeadline.text = user.login
            listItemRegion.textViewHeadline.text = user.region
        }
    }
}