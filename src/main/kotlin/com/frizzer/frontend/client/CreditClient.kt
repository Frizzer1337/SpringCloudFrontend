package com.frizzer.frontend.client

import com.frizzer.frontend.model.credit.CreditDto
import com.frizzer.frontend.model.login.PhoneLoginDto
import feign.Headers
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@ReactiveFeignClient(name = "credit-api", url = "\${reactive-feign.client.gateway-api.url}")
@Service
interface CreditClient{
    @RequestMapping(value = ["/credit/phone/{phone}"],method = [RequestMethod.GET])
    @Headers("Content-Type: application/json")
    fun findCreditByPhone(@PathVariable phone: String?): Flux<CreditDto>

}