package hh.school.lesson_12_zemskov.ui.map_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hh.school.lesson_12_zemskov.data.repository.BridgesRepository
import hh.school.lesson_12_zemskov.ui.UiState
import hh.school.lesson_12_zemskov.ui.model.Bridge
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val bridgesRepository: BridgesRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<UiState<List<Bridge>>>()
    val uiState: LiveData<UiState<List<Bridge>>> get() = _uiState

    init {
        loadBridges()
    }

    fun loadBridges() {
        _uiState.postValue(UiState.Loading)
        viewModelScope.launch {
            runCatching {
                bridgesRepository.getBridges()
            }.onSuccess { bridges ->
                val value = if (bridges.isNotEmpty()) {
                    UiState.Success(bridges)
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