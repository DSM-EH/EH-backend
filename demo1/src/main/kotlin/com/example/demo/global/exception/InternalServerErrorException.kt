package com.example.demo.global.exception

import com.example.demo.global.error.CustomException
import com.example.demo.global.error.GlobalErrorCode

object InternalServerErrorException: CustomException(
    GlobalErrorCode.INTERNAL_SERVER_ERROR
)