package com.example.demo.global.security

import com.example.demo.domain.auth.RefreshTokenEntity
import com.example.demo.domain.auth.RefreshTokenRepository
import com.example.demo.domain.auth.TokenResponse
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.Date

@Component
class JwtProvider(
    private val securityProperties: SecurityProperties,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    companion object {
        const val ACCESS = "access"
        const val REFRESH = "refresh"
        const val AUTHORITY = "authority"
    }

    fun receiveToken(userId: String) = TokenResponse(
        accessToken = generateAccessToken(userId),
        expiredAt = LocalDateTime.now().plusSeconds(securityProperties.accessExp.toLong()),
        refreshToken = generateRefreshToken(userId),
    )

    private fun generateAccessToken(userId: String) =
        Jwts.builder()
            .signWith(securityProperties.key, SignatureAlgorithm.HS512)
            .setHeaderParam(Header.JWT_TYPE, ACCESS)
            .setId(userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + securityProperties.accessExp))
            .compact()

    private fun generateRefreshToken(userId: String): String {
        val token = Jwts.builder()
            .signWith(securityProperties.key, SignatureAlgorithm.HS512)
            .setHeaderParam(Header.JWT_TYPE, REFRESH)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + securityProperties.refreshExp))
            .compact()

        val refreshToken = RefreshTokenEntity(
            token = token,
            userId = userId,
            expirationTime = securityProperties.refreshExp / 1000
        )
        refreshTokenRepository.save(refreshToken)

        return token
    }
}