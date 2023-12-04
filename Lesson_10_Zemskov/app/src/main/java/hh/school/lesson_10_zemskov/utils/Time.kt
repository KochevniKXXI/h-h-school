package hh.school.lesson_10_zemskov.utils

data class Time constructor(
    private val hours: Int,
    private val minutes: Int
) : Comparable<Time> {
    companion object {
        fun parse(source: String): Time {
            val list = source.split(":").take(2).toMutableList()
            if (list.size == 2) {
                list[0] = list[0].substringAfterLast(' ')
                list[1] = list[1].substringBefore(' ')
                return Time(list[0].toInt(), list[1].toInt())
            } else throw IllegalArgumentException()
        }
    }

    fun until(other: Time) =
        if (other.minutes >= this.minutes) {
            if (other.hours >= this.hours) {
                Time(other.hours - this.hours, other.minutes - this.minutes)
            } else {
                Time(24 - this.hours + other.hours, other.minutes - this.minutes)
            }
        } else {
            if (other.hours >= this.hours) {
                Time(other.hours - this.hours - 1, 60 - this.minutes + other.minutes)
            } else {
                Time(24 - this.hours + other.hours - 1, 60 - this.minutes + other.minutes)
            }
        }

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
        } else {
            value >= start || value <= endInclusive
        }
    }
}