package com.frizzer.frontend.controller

import com.frizzer.frontend.client.CreditClient
import com.frizzer.frontend.controller.model.CREDITS
import com.frizzer.frontend.controller.session.PHONE
import com.frizzer.frontend.model.credit.CreditDto
import com.frizzer.frontend.model.login.PhoneLoginDto
import feign.FeignException.NotFound
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.WebSession
import reactor.core.publisher.Flux

@Controller
@RequestMapping("/credit")
class CreditHandler(val creditClient: CreditClient) {

    @PostMapping("/phone")
    fun findCreditById(session: WebSession, model: Model): Flux<CreditDto> {
        var creditDto = Flux.empty<CreditDto>()
        if (session.attributes[PHONE] is String) {
            val phoneDto = PhoneLoginDto(session.attributes[PHONE] as String)
            creditDto = creditClient.findCreditByPhone(phoneDto.phone)
                .doOnError{log.error("Error while finding credits $it")}
                .onErrorReturn({x -> x is NotFound}, CreditDto())
        }
        model.addAttribute(CREDITS,creditDto)
        return creditDto
    }

    private companion object{
        private val log = LoggerFactory.getLogger(CreditHandler::class.java)
    }

}