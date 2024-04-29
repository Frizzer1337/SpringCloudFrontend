package com.frizzer.frontend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import reactivefeign.spring.config.EnableReactiveFeignClients

@SpringBootApplication
@EnableFeignClients
@EnableReactiveFeignClients
class SpringBankingFrontendApplication

fun main(args: Array<String>) {
    runApplication<SpringBankingFrontendApplication>(*args)
}
