package ru.greevzy.highload.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.util.DigestUtils
import ru.greevzy.highload.exception.UserNotFoundException
import ru.greevzy.highload.exception.WrongPasswordException
import ru.greevzy.highload.mapper.UserMapper
import ru.greevzy.highload.model.dto.LoginRequestDTO
import ru.greevzy.highload.model.dto.LoginResponseDTO
import ru.greevzy.highload.model.dto.RegisterUserRequestDTO
import ru.greevzy.highload.model.dto.RegisterUserResponseDTO
import ru.greevzy.highload.model.dto.UserDTO
import ru.greevzy.highload.model.entity.UserAuthDataEntity
import ru.greevzy.highload.model.entity.UserEntity
import ru.greevzy.highload.repository.UserAuthDataRepository
import ru.greevzy.highload.repository.UserRepository
import java.util.UUID
import javax.transaction.Transactional


@Service
class UserService(
    private val userRepository: UserRepository,
    private val authDataRepository: UserAuthDataRepository
) {

    @Transactional
    fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO {
        log.debug("Authenticate request for user ${loginRequestDTO.userId} ...")

        val userId = loginRequestDTO.userId
        val authEntity = authDataRepository.findByUserId(userId)
            ?: run {
                log.debug("User ${loginRequestDTO.userId} not registered")
                throw UserNotFoundException(userId)
            }

        val encryptedPassword = DigestUtils.md5DigestAsHex(loginRequestDTO.password.toByteArray())

        return when (authEntity.password == encryptedPassword) {
            true -> {
                val token = UUID.randomUUID()
                authDataRepository.updateToken(userId, token.toString())
                log.debug("User ${loginRequestDTO.userId} successfully authenticated")
                return LoginResponseDTO(token.toString())
            }

            false -> run {
                log.debug("Password for user ${loginRequestDTO.userId} is not correct")
                throw WrongPasswordException(userId)
            }
        }
    }

    fun searchUsers(firstNamePart: String, secondNamePart: String):List<UserDTO> {
        log.debug("Searching users with params firstNamePart=$firstNamePart%, secondNamePart=$secondNamePart% ..." )

        return userRepository.searchUsersByParams("$firstNamePart%", "$secondNamePart%")
            .map { UserMapper.entityToDto(it) }
    }

    @Transactional
    fun register(requestDTO: RegisterUserRequestDTO): RegisterUserResponseDTO {
        log.debug("Trying to register user with firstName=${requestDTO.firstName} and secondName=${requestDTO.secondName} ...")

        val userId = UUID.randomUUID().toString()
        val userEntity = UserEntity(
            id = userId,
            firstName = requestDTO.firstName,
            secondName = requestDTO.secondName,
            birthdate = requestDTO.birthdate,
            sex = requestDTO.sex,
            biography = requestDTO.biography,
            city = requestDTO.city
        )

        val encryptedPassword = DigestUtils.md5DigestAsHex(requestDTO.password.toByteArray())
        val authDataEntity = UserAuthDataEntity(
            password = encryptedPassword,
            token = UUID.randomUUID().toString(),
            user = userEntity
        )

        userRepository.save(userEntity)
        authDataRepository.save(authDataEntity)

        log.debug("User with firstName=${requestDTO.firstName} and secondName=${requestDTO.secondName} successfully registered with id=$userId")
        return RegisterUserResponseDTO(userId)
    }

    fun findById(userId: String): UserDTO {
        val entity = userRepository.findUserById(userId)
            ?: throw UserNotFoundException(userId)

        return UserDTO(
            userId = entity.id,
            firstName = entity.firstName,
            secondName = entity.secondName,
            birthdate = entity.birthdate,
            biography = entity.biography,
            city = entity.city
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(UserService::class.java)
    }
}