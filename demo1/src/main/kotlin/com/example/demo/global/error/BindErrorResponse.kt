package com.example.demo.global.error

data class BindErrorResponse(
    val status: Int,
    val fieldError: List<Map<String, String?>>
)