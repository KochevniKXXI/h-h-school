package hh.school.lesson_7_zemskov.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface InfoUiState : Parcelable {
    @Parcelize
    data class Error(val exception: Throwable? = null) : InfoUiState
    @Parcelize
    data object Loading : InfoUiState
    @Parcelize
    data object Empty : InfoUiState
}