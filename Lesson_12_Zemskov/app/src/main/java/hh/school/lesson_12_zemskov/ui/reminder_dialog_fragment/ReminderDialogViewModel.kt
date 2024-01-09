package hh.school.lesson_12_zemskov.ui.reminder_dialog_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hh.school.lesson_12_zemskov.data.repository.ReminderRepository
import javax.inject.Inject

class ReminderDialogViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ViewModel() {
    private val _reminder = MutableLiveData(0)
    val reminder: LiveData<Int> get() = _reminder

    fun loadSavedReminder(bridgeId: Int) =
        _reminder.postValue(reminderRepository.getReminderById(bridgeId))

    fun saveReminder(bridgeId: Int, time: Int) = reminderRepository.saveReminder(bridgeId, time)

    fun removeReminder(bridgeId: Int) = reminderRepository.removeReminder(bridgeId)
}