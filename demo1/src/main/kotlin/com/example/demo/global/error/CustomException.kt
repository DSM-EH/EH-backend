package com.example.demo.global.error

abstract class CustomException (
    val errorProperty: CustomErrorProperty
): RuntimeException() {
    override fun fillInStackTrace() = this
}