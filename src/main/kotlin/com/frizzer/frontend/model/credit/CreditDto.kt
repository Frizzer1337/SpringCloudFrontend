package com.frizzer.frontend.model.credit

import java.time.LocalDateTime

data class CreditDto(
    val id: Int? = null,
    val type: String? = null,
    var percent: Double? = null,
    val lastPaymentDate: LocalDateTime? = null,
    val balance: Double? = null,
    val penalty: Double? = null,
    val clientId: Int? = null,
    var status: CreditStatus? = null
)