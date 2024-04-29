package com.frizzer.frontend.validator

import com.frizzer.frontend.model.LoginDto
import com.frizzer.frontend.model.PhoneLoginDto
import com.frizzer.frontend.model.StatusCode
import org.springframework.stereotype.Component

@Component
class LoginValidator {

    fun validate(phoneLogin: PhoneLoginDto): StatusCode {
        val phone = phoneLogin.phone.orEmpty()
        return when{
            !phone.matches(PHONE_REGEX) -> StatusCode.INVALID_PHONE
            else -> StatusCode.PHONE_OK
        }
    }

    fun validate(login: LoginDto): StatusCode {
        val phone = login.phone.orEmpty()
        val password = login.password.orEmpty()
        return when{
            !phone.matches(PHONE_REGEX) -> StatusCode.INVALID_PHONE
            !password.matches(PASSWORD_REGEX) -> StatusCode.INVALID_PASSWORD
            else -> StatusCode.OK
        }

    }

    companion object{
        private val PHONE_REGEX =
            Regex("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$")
        //Latin letters, minimum eight characters, at least one letter and one number:
        private val PASSWORD_REGEX= Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    }
}