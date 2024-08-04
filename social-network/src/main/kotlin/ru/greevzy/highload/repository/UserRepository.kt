package ru.greevzy.highload.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.greevzy.highload.model.entity.UserEntity

@Repository
interface UserRepository: CrudRepository<UserEntity, String> {

    @Query(value = "select * from users where id = :id", nativeQuery = true)
    fun findUserById(id: String): UserEntity?

    @Query(value = "select * from users where first_name like :firstNamePart and second_name like :secondNamePart order by id", nativeQuery = true)
    fun searchUsersByParams(firstNamePart: String, secondNamePart: String): List<UserEntity>
}