package ru.greevzy.highload.model.dto

import ru.greevzy.highload.model.enums.Sex
import java.time.LocalDate
import javax.validation.constraints.Max
import javax.validation.constraints.Past


data class RegisterUserRequestDTO(
    val password: String,
    @Max(100)
    val firstName: String,
    @Max(100)
    val secondName: String,
    @Past
    val birthdate: LocalDate,
    val sex: Sex,
    @Max(1000)
    val biography: String,
    @Max(50)
    val city: String
)