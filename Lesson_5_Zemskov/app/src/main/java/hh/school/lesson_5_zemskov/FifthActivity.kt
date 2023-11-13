package hh.school.lesson_5_zemskov

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import hh.school.lesson_5_zemskov.databinding.ActivityFifthBinding
import hh.school.lesson_5_zemskov.model.Data

class FifthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFifthBinding
    private lateinit var data: Data

    companion object {
        const val KEY_RESULT_TEXT = "key_result_text"
        private const val KEY_DATA = "key_data"

        fun createStartIntent(context: Context) = Intent(context, FifthActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = savedInstanceState?.getParcelable(KEY_DATA) ?: Data()

        binding = ActivityFifthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonDeliverResult.setOnClickListener {
            setResult(
                Activity.RESULT_OK,
                Intent().apply {
                    putExtra(KEY_RESULT_TEXT, binding.editTextResult.text?.toString().orEmpty())
                }
            )
            finish()
        }

        binding.editTextValueData.addTextChangedListener {
            binding.textInputLayoutValueData.isErrorEnabled = false
        }

        binding.buttonSave.setOnClickListener {
            val valueData = binding.editTextValueData.text?.toString().orEmpty()
            if (valueData.isBlank()) {
                binding.textInputLayoutValueData.error =
                    getString(R.string.edit_text_value_data_error_text)
            } else {
                data.value = valueData
                binding.textViewValueData.text = data.value
            }
        }

        data.value?.let { binding.textViewValueData.text = it }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(KEY_DATA, data)
        super.onSaveInstanceState(outState)
    }
}