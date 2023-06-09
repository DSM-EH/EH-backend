package com.example.demo.global.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtParser: JwtParser
): OncePerRequestFilter() {

    companion object {
        private const val PREFIX = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolvedToken(request)

        if(token != null) {
            SecurityContextHolder.getContext().authentication = jwtParser.getAuthentication(token)
        } else {
            SecurityContextHolder.clearContext()
        }

        filterChain.doFilter(request, response)
    }

    private fun resolvedToken(request: HttpServletRequest): String? {
        val bearerToken: String? = request.getHeader(HttpHeaders.AUTHORIZATION)

        bearerToken?.let {
            if (bearerToken.startsWith(PREFIX)) {
                return bearerToken.substring(PREFIX.length)
            }
        }

        return null
    }
}