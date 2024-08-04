package ru.greevzy.highload.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
        description = "Упрощенный процесс аутентификации путем передачи идентификатор пользователя и получения токена для дальнейшего прохождения авторизации"
    )
    @PostMapping(
        value = ["/login"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun login(@RequestBody requestDTO: LoginRequestDTO): LoginResponseDTO {
        return userService.login(requestDTO)
    }

    @Operation(summary = "Регистрация нового пользователя")
    @PostMapping(
        value = ["/user/register"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun register(@Valid @RequestBody requestDTO: RegisterUserRequestDTO): RegisterUserResponseDTO {
        return userService.register(requestDTO)
    }

    @Operation(summary = "Получение анкеты пользователя, требует аутентификации")
    @GetMapping(
        value = ["/user/get/{id}"],
        produces = ["application/json"]
    )
    @SecurityRequirement(name = "X-Token")
    fun getUserById(@PathVariable id: String): UserDTO {
        return userService.findById(id)
    }

    @Operation(
        summary = "Поиск анкет пользователей",
        parameters = [
            Parameter(
                name = "first_name",
                schema = Schema(
                    implementation = String::class,
                    description = "Часть имени для поиска",
                    example = "Конст"
                ),
                `in` = ParameterIn.QUERY,
                description = "Условие поиска по имени",
                required = true
            ),
            Parameter(
                name = "second_name",
                schema = Schema(
                    implementation = String::class,
                    description = "Часть фамилии для поиска",
                    example = "Оси"
                ),
                `in` = ParameterIn.QUERY,
                description = "Условие поиска по фамилии",
                required = true

            )
        ]
    )
    @GetMapping(
        value = ["/user/search"],
        produces = ["application/json"]
    )
    @SecurityRequirement(name = "X-Token")
    fun searchUsers(
        @RequestParam("first_name") firstNamePart: String,
        @RequestParam("second_name") secondNamePart: String
    ): List<UserDTO> {
        return userService.searchUsers(firstNamePart, secondNamePart)
    }
}