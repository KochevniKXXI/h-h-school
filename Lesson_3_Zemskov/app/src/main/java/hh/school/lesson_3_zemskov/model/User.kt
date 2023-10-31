package hh.school.lesson_3_zemskov.model

data class User(
    val cardNumber: String = "0000001",
    val name: String = "Иван",
    val surname: String = "Иванов",
    val email: String = "ivan@mail.ru",
    val login: String = "I032NA2V1028",
    val region: String = "Москва",
    val skill: String = "Специалист"
)
