package hh.school.lesson_12_zemskov.data.repository

import hh.school.lesson_12_zemskov.ui.model.Bridge
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    val reminders: Flow<Pair<String, Int>>
    fun getBridgesWithReminders(bridges: List<Bridge>): List<Bridge>
    fun getReminderById(id: Int): Int
    fun saveReminder(id: Int, time: Int)
    fun removeReminder(id: Int)
}