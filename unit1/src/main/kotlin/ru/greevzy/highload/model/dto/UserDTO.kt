package ru.greevzy.highload.model.dto

import java.time.LocalDate


data class UserDTO(
    val userId: String,
    val firstName: String,
    val secondName: String,
    val birthdate: LocalDate,
    val biography: String,
    val city: String
)