package hh.school.lesson_10_zemskov.model

import androidx.annotation.DrawableRes
import hh.school.lesson_10_zemskov.R

enum class BridgeState(@DrawableRes val imageResId: Int) {
    NORMAL(R.drawable.ic_brige_normal),
    LATE(R.drawable.ic_brige_late),
    SOON(R.drawable.ic_brige_soon)
}