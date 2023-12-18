package hh.school.lesson_12_zemskov.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import hh.school.lesson_12_zemskov.common.LiveSharedPreferences
import hh.school.lesson_12_zemskov.ui.model.Bridge
import javax.inject.Inject

class OfflineReminderRepository @Inject constructor(
    private val preferences: SharedPreferences
) : ReminderRepository {
    override val reminders by lazy { LiveSharedPreferences(preferences) }

    override fun getBridgesWithReminders(bridges: List<Bridge>): List<Bridge> =
        bridges.map { bridge -> bridge.copy(reminder = getReminderById(bridge.id)) }

    override fun getReminderById(id: Int) = preferences.getInt(id.toString(), 0)

    override fun saveReminder(id: Int, time: Int) =
        preferences.edit {
            putInt(id.toString(), time)
        }

    override fun removeReminder(id: Int) =
        preferences.edit {
            remove(id.toString())
        }
}