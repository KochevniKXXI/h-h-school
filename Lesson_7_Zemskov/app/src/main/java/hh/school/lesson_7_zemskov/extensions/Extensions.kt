package hh.school.lesson_7_zemskov.extensions

import hh.school.lesson_7_zemskov.model.Divorce

fun List<Divorce>.asString(): String {
    return this.fold("") { acc, divorce ->
        "$acc    $divorce"
    }.trim()
}