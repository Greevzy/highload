package ru.greevzy.highload

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication


@SpringBootApplication
@ConfigurationPropertiesScan
class SocialNetworkApplication

fun main() {
    runApplication<SocialNetworkApplication>()
}