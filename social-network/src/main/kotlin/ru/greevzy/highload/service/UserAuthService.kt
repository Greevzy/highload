package ru.greevzy.highload.service

import org.springframework.stereotype.Service
import ru.greevzy.highload.model.domain.UserAuthData
import ru.greevzy.highload.repository.UserAuthDataRepository


@Service
class UserAuthService(private val userAuthDataRepository: UserAuthDataRepository) {

    fun getAuthData(token: String): UserAuthData? {
        return userAuthDataRepository.findUserAuthDataByToken(token)
            ?.let {
                if (it.token == null) null
                else UserAuthData(it.user.id, it.token)
            }
    }
}