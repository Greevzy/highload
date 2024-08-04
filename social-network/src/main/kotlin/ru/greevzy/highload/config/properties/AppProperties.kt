package ru.greevzy.highload.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties("social-network")
data class AppProperties(
    val secureUri: Set<String>
)