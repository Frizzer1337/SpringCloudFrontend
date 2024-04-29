package com.frizzer.frontend.controller

import com.frizzer.frontend.client.AuthClient
import com.frizzer.frontend.controller.model.LOGIN_DTO
import com.frizzer.frontend.controller.routes.INDEX
import com.frizzer.frontend.controller.routes.LOGIN
import com.frizzer.frontend.controller.session.STATUS_CODE
import com.frizzer.frontend.model.LoginDto
import com.frizzer.frontend.model.StatusCode
import com.frizzer.frontend.validator.LoginValidator
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono

@Controller
@RequestMapping("/login")
class LoginHandler(
    val authClient: AuthClient,
    val loginValidator: LoginValidator
) {
    @GetMapping("")
    fun get(
        model: Model,
        session: WebSession
    ): Mono<String> {
        model.addIfEmpty(LOGIN_DTO, LoginDto())
        session.addIfEmpty(STATUS_CODE, StatusCode.OK)
        return Mono.just(LOGIN)
    }

    @PostMapping("")
    fun post(
        model: Model,
        loginDto: LoginDto,
        session: WebSession
    ): Mono<String> {
        val loginStatus = Mono.just(loginValidator.validate(loginDto))
            .doOnNext { session.attributes[STATUS_CODE] = it }
            .filter { it == StatusCode.OK }
            .flatMap { login(loginDto) }
            .doOnNext { session.attributes[STATUS_CODE] = it }
        model.addAttribute(loginStatus)
        return loginStatus.then(Mono.just(LOGIN))
    }

    private fun login(loginDto: LoginDto): Mono<StatusCode> {
        return authClient.login(Mono.just(loginDto))
            .map { loginResult ->
                when (loginResult) {
                    true -> StatusCode.OK
                    false -> StatusCode.NOT_FOUND
                }
            }
    }
}

fun Model.addIfEmpty(name: String, value: Any) {
    if (!this.containsAttribute(name)) {
        this.addAttribute(value)
    }
}

fun WebSession.addIfEmpty(name: String, value: Any) {
    if (!this.attributes.containsKey(name)) {
        this.attributes[name] = value
    }
}