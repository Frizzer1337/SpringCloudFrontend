package com.frizzer.frontend.client

import com.frizzer.frontend.model.login.LoginDto
import com.frizzer.frontend.model.login.PhoneLoginDto
import feign.Headers
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono

@ReactiveFeignClient(name = "auth-api", url = "\${reactive-feign.client.gateway-api.url}")
@Service
interface AuthClient{
    @RequestMapping(value = ["/client/login"],method = [RequestMethod.POST])
    @Headers("Content-Type: application/json")
    fun login(loginDto: Mono<LoginDto>): Mono<Boolean>

    @RequestMapping(value = ["/client/login/phone"],method = [RequestMethod.POST])
    @Headers("Content-Type: application/json")
    fun phoneLogin(phoneLoginDto: Mono<PhoneLoginDto>): Mono<Boolean>
}