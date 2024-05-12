package com.frizzer.frontend.controller

import com.frizzer.frontend.client.AuthClient
import com.frizzer.frontend.controller.model.LOGIN_DTO
import com.frizzer.frontend.controller.model.PHONE_LOGIN_DTO
import com.frizzer.frontend.controller.routes.LOGIN
import com.frizzer.frontend.controller.session.LOGGED_IN
import com.frizzer.frontend.controller.session.PHONE
import com.frizzer.frontend.controller.session.STATUS_CODE
import com.frizzer.frontend.model.login.LoginDto
import com.frizzer.frontend.model.login.PhoneLoginDto
import com.frizzer.frontend.model.StatusCode
import com.frizzer.frontend.validator.LoginValidator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
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
        model.addIfEmpty(PHONE_LOGIN_DTO, PhoneLoginDto())
        model.addIfEmpty(LOGIN_DTO, LoginDto())
        return Mono.just(LOGIN)
    }

    @PostMapping("/phone")
    fun phoneLogin(
        model: Model,
        @ModelAttribute login: LoginDto,
        loginDto: PhoneLoginDto,
        session: WebSession
    ): Mono<String> {
        val loginStatus = Mono.just(loginValidator.validate(loginDto))
            .doOnNext { session.attributes[STATUS_CODE] = it }
            .filter { it == StatusCode.PHONE_OK }
            .flatMap { phoneLogin(loginDto) }
            .doOnNext { session.attributes[STATUS_CODE] = it }
        model.addAttribute(loginDto)
        return loginStatus.then(Mono.just(LOGIN))
    }

    @PostMapping("")
    fun login(
        model: Model,
        @ModelAttribute phoneLoginDto: PhoneLoginDto,
        loginDto: LoginDto,
        session: WebSession
    ): Mono<String> {
        val loginStatus = Mono.just(loginValidator.validate(loginDto))
            .doOnNext { session.attributes[STATUS_CODE] = it }
            .filter { it == StatusCode.OK }
            .flatMap { login(loginDto) }
            .doOnNext { session.attributes[STATUS_CODE] = it }
            .filter { it == StatusCode.OK }
            .doOnNext { addLoginToSession(session, loginDto) }
        model.addAttribute(loginStatus)
        return loginStatus.then(Mono.just(LOGIN))
    }

    private fun addLoginToSession(session: WebSession, login: LoginDto) {
        session.attributes[LOGGED_IN] = true
        session.attributes[PHONE] = login.phone
    }

    private fun phoneLogin(phoneLoginDto: PhoneLoginDto): Mono<StatusCode> {
        return authClient.phoneLogin(Mono.just(phoneLoginDto))
            .map { loginResult ->
                when (loginResult) {
                    true -> StatusCode.PHONE_OK
                    false -> StatusCode.NOT_FOUND
                }
            }
            .doOnError { log.error("Error while phone login", it) }
            .onErrorReturn(StatusCode.INTERNAL_ERROR)
    }

    private fun login(loginDto: LoginDto): Mono<StatusCode> {
        return authClient.login(Mono.just(loginDto))
            .map { loginResult ->
                when (loginResult) {
                    true -> StatusCode.OK
                    false -> StatusCode.NOT_FOUND
                }
            }
            .onErrorReturn(StatusCode.INTERNAL_ERROR)
    }

    private companion object{
        private val log = LoggerFactory.getLogger(LoginHandler::class.java)
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