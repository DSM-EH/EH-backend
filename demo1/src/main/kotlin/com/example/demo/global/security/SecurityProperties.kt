package com.example.demo.global.security

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import java.security.Key
import java.util.Base64

@ConfigurationProperties(prefix = "jwt")
class SecurityProperties(
    secretKey: String,
    accessExp: Int,
    refreshExp: Int
) {
    val key: Key by lazy {
        val secretKey: String = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }
    val accessExp: Int = accessExp * 1000
    val refreshExp: Int = refreshExp * 1000
}