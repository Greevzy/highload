package ru.greevzy.highload.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.greevzy.highload.model.entity.UserAuthDataEntity
import ru.greevzy.highload.model.entity.UserEntity
import java.util.UUID

@Repository
interface UserAuthDataRepository : CrudRepository<UserAuthDataEntity, Long> {

    @Query("select * from user_auth_data where user_id = ?1", nativeQuery = true)
    fun findByUserId(userId: String): UserAuthDataEntity?

    @Modifying
    @Query(value = "update user_auth_data set token = :token where user_id = :id", nativeQuery = true)
    fun updateToken(id: String, token: String)

    @Query(
        value = """
        select * 
          from user_auth_data uad 
          join users u on u.id = uad.user_id 
         where uad.token = :token
    """, nativeQuery = true
    )
    fun findUserAuthDataByToken(token: String): UserAuthDataEntity?
}