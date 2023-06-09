package com.example.demo.global.exception

import com.example.demo.global.error.CustomException
import com.example.demo.global.error.GlobalErrorCode

object CommentNotFoundException : CustomException(
    GlobalErrorCode.COMMENT_NOT_FOUND
)