package com.frizzer.frontend.controller

import com.frizzer.frontend.controller.routes.ACCOUNT
import com.frizzer.frontend.model.credit.CreditDto
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Controller
@RequestMapping("/account")
class AccountHandler(val creditHandler: CreditHandler) {

    @GetMapping("")
    fun get(model : Model,session: WebSession): Mono<String> {
        val credits = creditHandler.findCreditById(session, model)
        model.addAttribute(credits)
        return credits.then(ACCOUNT.toMono())
    }

}