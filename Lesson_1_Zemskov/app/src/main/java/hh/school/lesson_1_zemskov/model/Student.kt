package hh.school.lesson_1_zemskov.model

import java.util.UUID

data class Student(
    val id: UUID,
    val name: String,
    val surname: String,
    val grade: String,
    val birthdayYear: UShort
) {
    override fun toString(): String {
        return "id = $id\n" +
                "name = $name\n" +
                "surname = $surname\n" +
                "grade = $grade\n" +
                "birthdayYear = $birthdayYear"
    }
}
