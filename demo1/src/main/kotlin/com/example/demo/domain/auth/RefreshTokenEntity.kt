package com.example.demo.domain.auth

import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash("tbl_refresh_token")
data class RefreshTokenEntity(

    @Id
    val token: String,

    @field:NotNull
    val userId: String,

    @field:NotNull
    @TimeToLive
    val expirationTime: Int

)