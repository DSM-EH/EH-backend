package com.example.demo.global.exception

import com.example.demo.global.error.CustomException
import com.example.demo.global.error.GlobalErrorCode

object PostNotFoundException : CustomException(
    GlobalErrorCode.POST_NOT_FOUND
)