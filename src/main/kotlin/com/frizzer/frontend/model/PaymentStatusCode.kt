package com.frizzer.frontend.model

enum class PaymentStatusCode(
    val message: String,
    val code: Int
) {
    OK("OK", 200),
    CARD_OK("CARD_OK",201),
    NOT_VALID("Card not valid", 400),
    INTERNAL_ERROR("Internal error", 500)
}