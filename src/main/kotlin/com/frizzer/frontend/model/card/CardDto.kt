package com.frizzer.frontend.model.card

import org.springframework.format.annotation.DateTimeFormat
import java.time.YearMonth

data class CardDto(
    var number: String? = null,
    var cvv : Int? = null,
    var cardholderName : String? = null,
    @DateTimeFormat(pattern = "MM/yy")
    var expiryDate : YearMonth? = null
)