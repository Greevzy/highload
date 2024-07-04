package ru.greevzy.highload.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@SecurityScheme(
    name = "X-Token",
    `in` = SecuritySchemeIn.HEADER,
    type = SecuritySchemeType.APIKEY
)
class SwaggerConfig(private val buildProperties: BuildProperties?) {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI().info(apiInfo())
            .addServersItem(Server().url("/"))
    }

    private fun apiInfo(): Info = Info().title("Social Network Service")
        .description("Сервис-тест социальная сеть")
        .version(buildProperties?.version ?: "")
        .contact(
            Contact().name("sabakumov")
                .email("")
                .url("")
        )
}