package com.example.demo.domain.auth

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : CrudRepository<RefreshTokenEntity, String> {
    fun findByToken(token: String): RefreshTokenEntity?
}