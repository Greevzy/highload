package ru.greevzy.highload.exception


class WrongPasswordException(userId: String) : AbstractSocialNetworkException(
    message = "Received wrong password for user id $userId"
)