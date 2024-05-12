package com.frizzer.frontend.client

import com.frizzer.frontend.model.payment.PaymentDto
import feign.Headers
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono

@ReactiveFeignClient(name = "payment-api", url = "\${reactive-feign.client.gateway-api.url}")
@Service
interface PaymentClient {
    @RequestMapping(value = ["/payment/phone"], method = [RequestMethod.POST])
    @Headers("Content-Type: application/json")
    fun pay(@RequestBody paymentDto: PaymentDto): Mono<Boolean>
}