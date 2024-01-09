package hh.school.lesson_12_zemskov.ui.list_bridges_fragment

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

class ListBridgesViewModel @Inject constructor(
    private val bridgesRepository: BridgesRepository,
    private val reminderRepository: ReminderRepository
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
                if (bridges.isNotEmpty()) {
                    _uiState.postValue(
                        UiState.Success(
                            reminderRepository.getBridgesWithReminders(
                                bridges
                            )
                        )
                    )
                    reminderRepository.reminders.collect {
                        _uiState.postValue(
                            UiState.Success(
                                reminderRepository.getBridgesWithReminders(
                                    bridges
                                )
                            )
                        )
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