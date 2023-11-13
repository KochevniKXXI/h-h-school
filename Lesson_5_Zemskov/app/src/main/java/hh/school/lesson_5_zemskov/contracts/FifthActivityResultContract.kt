package hh.school.lesson_5_zemskov.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import hh.school.lesson_5_zemskov.FifthActivity

class FifthActivityResultContract : ActivityResultContract<Unit, String?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return FifthActivity.createStartIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return if (resultCode == Activity.RESULT_OK) {
            val resultText = intent?.getStringExtra(FifthActivity.KEY_RESULT_TEXT).orEmpty()
            resultText.ifBlank {
                "Передана пустая строка"
            }
        } else {
            null
        }
    }
}