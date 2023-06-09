package com.example.demo.domain.auth

import java.time.LocalDateTime

data class TokenResponse(
    val accessToken: String?,
    val expiredAt: LocalDateTime?,
    val refreshToken: String?
)