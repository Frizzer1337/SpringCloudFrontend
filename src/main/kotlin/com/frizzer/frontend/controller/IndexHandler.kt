package com.frizzer.frontend.controller

import com.frizzer.frontend.controller.routes.INDEX
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Controller
@RequestMapping("/")
class IndexHandler {
    @GetMapping("")
    fun get(): Mono<String> {
        val model = {"salute" to "hi"}
        return Mono.just(INDEX)
    }
}