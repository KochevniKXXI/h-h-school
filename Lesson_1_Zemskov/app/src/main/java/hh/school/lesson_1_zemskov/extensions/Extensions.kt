package hh.school.lesson_1_zemskov.extensions

fun String.removeExtraSpaces() = replace("\\s+".toRegex(), " ").trim()

fun List<String>.toStringForDisplay() = toString().replace("[\\[\\], ]".toRegex(), "")
