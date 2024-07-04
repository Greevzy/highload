package ru.greevzy.highload.filter

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import ru.greevzy.highload.config.properties.AppProperties
import ru.greevzy.highload.service.UserAuthService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class TokenFilter(
    private val appProperties: AppProperties,
    private val userAuthService: UserAuthService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader(TOKEN_HEADER)

        if (token != null ) {
            val authData = userAuthService.getAuthData(token)
            if (authData != null) {
                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(authData.userId, "", emptyList())
            }
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return appProperties.secureUri.none { pathMatcher.match(it, request.requestURI) }
    }

    companion object {
        private const val TOKEN_HEADER = "X-Token"

        private val pathMatcher = AntPathMatcher()
    }
}