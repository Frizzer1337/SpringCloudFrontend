package com.frizzer.frontend.controller

import com.frizzer.frontend.client.PaymentClient
import com.frizzer.frontend.controller.routes.PAYMENT
import com.frizzer.frontend.controller.session.PHONE
import com.frizzer.frontend.model.PaymentStatusCode
import com.frizzer.frontend.model.card.CardDto
import com.frizzer.frontend.model.payment.PaymentDto
import com.frizzer.frontend.validator.CardValidator
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.reactive.result.view.RedirectView
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Controller
@RequestMapping("/payment")
class PaymentHandler(
    val cardValidator: CardValidator,
    val paymentClient: PaymentClient
) {
    @GetMapping("")
    fun get(model: Model): Mono<String> {
        return PAYMENT.toMono()
    }

    @GetMapping("/{creditId}")
    fun get(model: Model, @PathVariable creditId: Int): Mono<String> {
        model.addAttribute(CardDto())
        return PAYMENT.toMono()
    }

    @PostMapping("/{creditId}")
    fun checkCard(cardDto: CardDto, model: Model, @PathVariable creditId: Int, session: WebSession): Mono<String> {
        validateCard(cardDto, model)
        model.addAttribute(cardDto)
        model.addAttribute(
            PaymentDto(
                phone = session.attributes[PHONE] as String,
                creditId = creditId
            )
        )
        return PAYMENT.toMono()
    }

    @PostMapping("/amount")
    fun pay(paymentDto: PaymentDto, model: Model): Mono<String> {
        model.addAttribute(CardDto())
        return paymentClient.pay(paymentDto).then(PAYMENT.toMono())
    }

    private fun validateCard(cardDto: CardDto, model: Model) {
        if (cardValidator.validate(cardDto)) {
            model.addAttribute(PaymentStatusCode.CARD_OK)
        } else {
            model.addAttribute(PaymentStatusCode.NOT_VALID)
        }
    }
}