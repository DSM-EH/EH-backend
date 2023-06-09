package com.example.demo.domain.post.usecase

import com.example.demo.domain.group.GroupRepository
import com.example.demo.domain.post.Comment
import com.example.demo.domain.post.CommentRepository
import com.example.demo.domain.post.Post
import com.example.demo.domain.post.PostRepository
import com.example.demo.domain.user.UserRepository
import com.example.demo.global.exception.GroupNotFoundException
import com.example.demo.global.exception.PostNotFoundException
import com.example.demo.global.exception.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RequestMapping("/post")
@RestController
class PostUseCase(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val commentRepository: CommentRepository
) {

    data class CreatePostRequest(
        val userId: Int,
        val groupId: Int,
        val title: String,
        val content: String,
        val isPromotion: Boolean
    )

    @PostMapping
    fun createPost(@RequestBody request: CreatePostRequest): Int {
        return postRepository.save(
            Post(
                title = request.title,
                content = request.content,
                isPromotion = request.isPromotion,
                writer = userRepository.findByIdOrNull(request.userId) ?: throw UserNotFoundException,
                group = groupRepository.findByIdOrNull(request.groupId) ?: throw GroupNotFoundException,
                createdAt = LocalDateTime.now()
            )
        ).id
    }

    data class UpdatePostRequest(
        val postId: Int,
        val title: String,
        val content: String
    )

    @PatchMapping
    fun updatePost(@RequestBody request: UpdatePostRequest) {
        val post = postRepository.findByIdOrNull(request.postId) ?: throw PostNotFoundException

        postRepository.save(
            Post(
                id = post.id,
                title = request.title,
                content = request.content,
                isPromotion = post.isPromotion,
                writer = post.writer,
                group = post.group,
                createdAt = post.createdAt
            )
        )
    }

    @DeleteMapping
    fun deletePost(@RequestParam("id") id: Int) {
        postRepository.deleteById(id)
    }

    @GetMapping
    fun getGroupPost(@RequestParam("id") id: Int): List<Post> {
        return postRepository.findByGroupId(id)
    }

    data class CreateCommentRequest(
        val userId: Int,
        val postId: Int,
        val content: String
    )

    @PostMapping("/comment")
    fun createComment(@RequestBody request: CreateCommentRequest): Int {
        return commentRepository.save(
            Comment(
                content = request.content,
                post = postRepository.findByIdOrNull(request.postId) ?: throw PostNotFoundException,
                writer = userRepository.findByIdOrNull(request.userId) ?: throw UserNotFoundException
            )
        ).id
    }

    @GetMapping("/comment")
    fun getComments(@RequestParam("id") id: Int): List<Comment> {
        return commentRepository.findByPostId(id)
    }

    @DeleteMapping("/comment")
    fun deleteComment(@RequestParam("id") id: Int) {
        commentRepository.deleteById(id)
    }
}