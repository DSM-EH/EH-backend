package com.example.demo.global.exception

import com.example.demo.global.error.CustomException
import com.example.demo.global.error.GlobalErrorCode

object UserNotFoundException : CustomException(
    GlobalErrorCode.USER_NOT_FOUND
)