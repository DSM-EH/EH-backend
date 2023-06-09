package com.example.demo.domain.post

import org.springframework.data.repository.CrudRepository

interface CommentRepository: CrudRepository<Comment, Int> {
    fun findByPostId(postId: Int): List<Comment>
}