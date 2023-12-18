package hh.school.lesson_12_zemskov.ui.details_bridge_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hh.school.lesson_12_zemskov.data.repository.BridgesRepository
import hh.school.lesson_12_zemskov.data.repository.ReminderRepository
import hh.school.lesson_12_zemskov.ui.UiState
import hh.school.lesson_12_zemskov.ui.model.Bridge
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsBridgeViewModel @Inject constructor(
    private val bridgesRepository: BridgesRepository,
    private val reminderRepository: ReminderRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<UiState<Bridge>>(UiState.Loading)
    val uiState: LiveData<UiState<Bridge>> get() = _uiState

    fun loadBridgeById(id: Int) {
        _uiState.postValue(UiState.Loading)
        viewModelScope.launch {
            runCatching {
                bridgesRepository.getBridgeById(id)
            }.onSuccess { bridge ->
                if (bridge.name.isNotBlank()) {
                    _uiState.postValue(UiState.Success(bridge.copy(reminder = reminderRepository.getReminderById(bridge.id))))
                    reminderRepository.reminders.observeForever { (bridgeId, reminder) ->
                        if (id == bridgeId.toInt()) _uiState.postValue(UiState.Success(bridge.copy(reminder = reminder)))
                    }
                } else {
                    _uiState.postValue(UiState.Empty)
                }
            }.onFailure { error ->
                _uiState.postValue(UiState.Error(error))
            }
        }
    }
}