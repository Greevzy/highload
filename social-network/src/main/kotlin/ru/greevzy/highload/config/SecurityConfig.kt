package ru.greevzy.highload.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import ru.greevzy.highload.config.properties.AppProperties
import ru.greevzy.highload.filter.TokenFilter
import ru.greevzy.highload.service.UserAuthService


@Configuration
class SecurityConfig(appProperties: AppProperties, userAuthService: UserAuthService) : WebSecurityConfigurerAdapter() {

    private val tokenFilter = TokenFilter(appProperties, userAuthService)

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .authorizeRequests()
            .antMatchers("/user/get/**").authenticated()
            .anyRequest().permitAll()
            .and()
            .addFilterAfter(tokenFilter, BasicAuthenticationFilter::class.java)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling().authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
    }
}