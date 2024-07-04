package ru.greevzy.highload.model.entity

import ru.greevzy.highload.model.enums.Sex
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @Column(name = "id")
    val id: String,
    @Column(name = "first_name")
    val firstName: String,
    @Column(name = "second_name")
    val secondName: String,
    @Column(name = "birth_date")
    val birthdate: LocalDate,
    @Enumerated(value = EnumType.STRING)
    val sex: Sex,
    val biography: String,
    val city: String,
)