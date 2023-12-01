package hh.school.lesson_10_zemskov.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hh.school.lesson_10_zemskov.data.repository.BridgesRepository
import hh.school.lesson_10_zemskov.model.Bridge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val bridgesRepository: BridgesRepository
) : ViewModel() {
    private val _mapUiState: MutableStateFlow<UiState<List<Bridge>>> =
        MutableStateFlow(UiState.Loading)
    val mapUiState: StateFlow<UiState<List<Bridge>>> get() = _mapUiState

    init {
        updateMapUiState()
    }

    fun updateMapUiState() {
        _mapUiState.value = UiState.Loading
        viewModelScope.launch {
            runCatching {
                bridgesRepository.getBridgesPoints()
            }.onSuccess { listBridges ->
                _mapUiState.value = if (listBridges.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(listBridges)
                }
            }.onFailure { error ->
                _mapUiState.value = UiState.Error(error)
            }
        }
    }
}

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val error: Throwable) : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data object Empty : UiState<Nothing>
}