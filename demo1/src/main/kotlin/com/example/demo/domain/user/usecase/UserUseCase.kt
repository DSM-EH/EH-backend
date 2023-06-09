package com.example.demo.domain.user.usecase

import com.example.demo.domain.user.User
import com.example.demo.domain.user.UserRepository
import com.example.demo.global.exception.UserNotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user")
@RestController
class UserUseCase(
    private val  userRepository: UserRepository
) {

    data class CreateUserRequest(
        val email: String,
        val password: String,
        val nickname: String,
        val description: String,
        val profileImageUrl: String
    )

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest) {
        userRepository.save(
            User(
                email = request.email,
                password = request.password,
                nickname = request.nickname,
                description = request.description,
                profileImageUrl = request.profileImageUrl
            )
        )
    }

    data class UpdateUserRequest(
        val email: String,
        val password: String,
        val nickname: String,
        val description: String,
        val profileImageUrl: String
    )

    @PatchMapping
    fun updateUser(@RequestBody request:UpdateUserRequest) {
        val user = userRepository.findByEmail(request.email) ?: throw UserNotFoundException

        val user2 = User(
            id = user.id,
            email = request.email,
            password = request.password,
            nickname = request.nickname,
            description = request.description,
            profileImageUrl = request.profileImageUrl
        )

        userRepository.save(user2)
    }

    @GetMapping
    fun getProfile(@RequestParam("email") email:String): User {
        return userRepository.findByEmail(email) ?: throw UserNotFoundException
    }
}