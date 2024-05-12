package com.frizzer.frontend.model.payment

data class PaymentDto(
    var phone : String? = null,
    var creditId : Int? = null,
    var amount : Double? = null,
)