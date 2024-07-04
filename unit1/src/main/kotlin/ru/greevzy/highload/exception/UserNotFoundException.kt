package ru.greevzy.highload.exception

import kotlin.RuntimeException


class UserNotFoundException(userId: String) : AbstractSocialNetworkException(
    message = "User with id $userId not found"
)