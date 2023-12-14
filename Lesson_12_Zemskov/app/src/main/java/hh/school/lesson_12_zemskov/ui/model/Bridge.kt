package hh.school.lesson_12_zemskov.ui.model

import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class Bridge(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val divorces: List<Divorce> = listOf(),
    val lat: Double? = null,
    val lng: Double? = null,
    val photoCloseUrl: String = "",
    val photoOpenUrl: String = ""
) {
    val state: BridgeState get() = calculateState()

    fun getDivorcesAsString(): String {
        return divorces.fold("") { acc, divorce ->
            "$acc    $divorce"
        }.trim()
    }

    private fun calculateState(): BridgeState {
        val timeFormatter = DateTimeFormatter.ofPattern("H:mm")
        val currentTime = LocalTime.now()
        val divorcesAsTime = divorces.map {
            LocalTime.parse(it.start, timeFormatter)..LocalTime.parse(it.end, timeFormatter)
        }

        return if (divorcesAsTime.any { currentTime in it }) BridgeState.LATE
        else if (divorcesAsTime.any { currentTime.plusHours(1) in it }) BridgeState.SOON
        else BridgeState.NORMAL
    }
}