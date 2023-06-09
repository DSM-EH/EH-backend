package com.example.demo.domain.post

import org.springframework.data.repository.CrudRepository

interface PostRepository: CrudRepository<Post, Int> {
    fun findByGroupId(groupId: Int): List<Post>
}