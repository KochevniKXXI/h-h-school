package hh.school.lesson_5_zemskov.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Data(
    var value: String? = null
) : Parcelable