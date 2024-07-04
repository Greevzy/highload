package ru.greevzy.highload.exception

import java.lang.RuntimeException


abstract class AbstractSocialNetworkException(override val message: String?): RuntimeException() {
}