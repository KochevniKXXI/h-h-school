package hh.school.lesson_7_zemskov.extensions

import hh.school.lesson_7_zemskov.data.model.Divorce

fun List<Divorce>?.asString(): String {
    return this?.fold("") { acc, divorce ->
        "$acc    $divorce"
    }?.trim() ?: ""
}