package com.frizzer.frontend.model.login

import com.frizzer.frontend.model.StatusCode

data class LoginModel(
    val loginDto: LoginDto = LoginDto(),
    val statusCode: StatusCode = StatusCode.OK
)