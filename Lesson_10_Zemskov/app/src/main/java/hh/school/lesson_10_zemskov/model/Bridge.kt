package hh.school.lesson_10_zemskov.model

import hh.school.lesson_10_zemskov.utils.Time
import java.util.Calendar

data class Bridge(
    val id: Int,
    val name: String,
    val divorces: List<Divorce>,
    val lat: Double?,
    val lng: Double?
) {
    val state: BridgeState get() = calculateState()

    fun getDivorcesAsString(): String {
        return divorces.fold("") { acc, divorce ->
            "$acc    $divorce"
        }.trim()
    }

    private fun calculateState(): BridgeState {
        val calendar = Calendar.getInstance()
        val currentTime = Time(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
        val divorcesAsTime = divorces.map {
            try {
                Time.parse(it.start)..Time.parse(it.end)
            } catch (e: Exception) {
                null
            }
        }
        return if (divorcesAsTime.any { it != null && currentTime in it }) {
            BridgeState.LATE
        } else if (divorcesAsTime.any { it != null && currentTime.until(it.start) <= Time(1, 0) }) {
            BridgeState.SOON
        } else {
            BridgeState.NORMAL
        }
    }
}
