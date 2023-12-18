package hh.school.lesson_12_zemskov.ui.model

import androidx.annotation.DrawableRes
import hh.school.lesson_12_zemskov.R

enum class BridgeState(@DrawableRes val imageResId: Int) {
    NORMAL(R.drawable.ic_bridge_normal),
    LATE(R.drawable.ic_bridge_late),
    SOON(R.drawable.ic_bridge_soon)
}