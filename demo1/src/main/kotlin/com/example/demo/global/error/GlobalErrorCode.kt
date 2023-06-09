package com.example.demo.global.error

enum class GlobalErrorCode(
    private val status: Int,
    private val message: String
) : CustomErrorProperty {
    BAD_REQUEST(400, "Bad Request"),

    INVALID_TOKEN(401, "Invalid Token"),
    EXPIRED_TOKEN(401, "Expired Token"),
    UNEXPECTED_TOKEN(401, "Unexpected Token"),
    INVALID_ROLE(401, "Invalid Role"),

    POST_NOT_FOUND(404, "Post Not Found"),
    USER_NOT_FOUND(404,"User Not Found"),
    GROUP_NOT_FOUND(404,"Group Not Found"),
    COMMENT_NOT_FOUND(404, "Comment Not Found"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    override fun status(): Int = status
    override fun message(): String = message
}