package hh.school.lesson_12_zemskov.ui.details_bridge_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hh.school.lesson_12_zemskov.data.repository.BridgesRepository
import hh.school.lesson_12_zemskov.ui.UiState
import hh.school.lesson_12_zemskov.ui.model.Bridge
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsBridgeViewModel @Inject constructor(
    private val bridgesRepository: BridgesRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<UiState<Bridge>>(UiState.Loading)
    val uiState: LiveData<UiState<Bridge>> get() = _uiState

    fun loadBridgeById(id: Int) {
        _uiState.postValue(UiState.Loading)
        viewModelScope.launch {
            runCatching {
                bridgesRepository.getBridgeById(id)
            }.onSuccess { bridge ->
                val value = if (bridge.name.isNotBlank()) {
                    UiState.Success(bridge)
                } else {
                    UiState.Empty
                }
                _uiState.postValue(value)
            }.onFailure { error ->
                _uiState.postValue(UiState.Error(error))
            }
        }
    }
}