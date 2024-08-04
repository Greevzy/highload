package ru.greevzy.highload.mapper

import ru.greevzy.highload.model.dto.UserDTO
import ru.greevzy.highload.model.entity.UserEntity


object UserMapper {

    fun entityToDto(entity: UserEntity): UserDTO = UserDTO(
        userId = entity.id,
        firstName = entity.firstName,
        secondName = entity.secondName,
        birthdate = entity.birthdate,
        biography = entity.biography,
        city = entity.city
    )
}