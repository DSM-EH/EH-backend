package com.example.demo.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "tbl_user")
@Entity
class User (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @field:Column(unique = true)
    val email: String,

    val password: String,

    val nickname: String,

    val description: String,

    val profileImageUrl: String
)