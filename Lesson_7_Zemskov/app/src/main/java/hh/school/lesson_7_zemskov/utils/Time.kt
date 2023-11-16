package hh.school.lesson_7_zemskov.utils

import kotlin.math.abs

data class Time constructor(
    private val hours: Int,
    private val minutes: Int
) : Comparable<Time> {
    companion object {
        fun parse(source: String?): Time {
            if (source == null) throw IllegalArgumentException()
            val list = source.split(":").take(2).toMutableList()
            if (list.size == 2) {
                list.forEachIndexed { index, string ->
                    if (index == 0) {
                        list[index] = string.substringAfterLast(' ')
                    } else {
                        list[index] = string.substringBefore(' ')
                    }
                }
            }

            return Time(list[0].toInt(), list[1].toInt())
        }
    }

    fun leftUntil(other: Time) = Time(abs(other.hours - this.hours), abs(other.minutes - this.minutes))

    override fun compareTo(other: Time) = compareValuesBy(
        this,
        other,
        { it.hours },
        { it.minutes }
    )

    operator fun rangeTo(that: Time) = TimeRange(this, that)
}

class TimeRange(override val start: Time, override val endInclusive: Time) : ClosedRange<Time> {
    override fun contains(value: Time): Boolean {
        return if (start <= endInclusive) {
            super.contains(value)
        }
        else {
            value >= start || value <= endInclusive
        }
    }
}