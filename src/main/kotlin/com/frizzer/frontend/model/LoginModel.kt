package com.frizzer.frontend.model
data class LoginModel(
    val loginDto: LoginDto = LoginDto(),
    val statusCode: StatusCode = StatusCode.OK
)