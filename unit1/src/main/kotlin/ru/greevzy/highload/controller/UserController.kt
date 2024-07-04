package ru.greevzy.highload.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.greevzy.highload.model.dto.LoginRequestDTO
import ru.greevzy.highload.model.dto.LoginResponseDTO
import ru.greevzy.highload.model.dto.RegisterUserRequestDTO
import ru.greevzy.highload.model.dto.RegisterUserResponseDTO
import ru.greevzy.highload.model.dto.UserDTO
import ru.greevzy.highload.service.UserService
import javax.validation.Valid


@RestController
class UserController(private val userService: UserService) {

    @Operation(
        summary = "Выполнение аутентификации пользователя",
        description = "Упрощенный процесс аутентификации путем передачи идентификатор пользователя и получения токена для дальнейшего прохождения авторизации")
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/login"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun login(@RequestBody requestDTO: LoginRequestDTO): LoginResponseDTO {
        return userService.login(requestDTO)
    }

    @Operation(summary = "Регистрация нового пользователя")
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/user/register"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun register(@Valid @RequestBody requestDTO: RegisterUserRequestDTO): RegisterUserResponseDTO {
        return userService.register(requestDTO)
    }

    @Operation(summary = "Получение анкеты пользователя, требует аутентификации")
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/user/get/{id}"],
        produces = ["application/json"]
    )
    @SecurityRequirement(name = "X-Token")
    fun getUserById(@PathVariable id: String): UserDTO {
        return userService.findById(id)
    }
}