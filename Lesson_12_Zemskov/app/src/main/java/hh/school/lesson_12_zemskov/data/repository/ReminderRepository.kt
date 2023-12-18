package hh.school.lesson_12_zemskov.data.repository

import androidx.lifecycle.LiveData
import hh.school.lesson_12_zemskov.ui.model.Bridge

interface ReminderRepository {
    val reminders: LiveData<Pair<String, Int>>
    fun getBridgesWithReminders(bridges: List<Bridge>): List<Bridge>
    fun getReminderById(id: Int): Int
    fun saveReminder(id: Int, time: Int)
    fun removeReminder(id: Int)
}