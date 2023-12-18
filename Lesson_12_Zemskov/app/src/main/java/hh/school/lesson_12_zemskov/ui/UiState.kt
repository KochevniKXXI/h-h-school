package hh.school.lesson_12_zemskov.ui

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val error: Throwable) : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data object Empty : UiState<Nothing>
}