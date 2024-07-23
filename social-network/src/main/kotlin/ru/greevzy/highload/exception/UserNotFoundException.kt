package ru.greevzy.highload.exception


class UserNotFoundException(userId: String) : AbstractSocialNetworkException(
    message = "User with id $userId not found"
)