package com.frizzer.frontend.validator

import com.frizzer.frontend.model.card.CardDto
import org.springframework.stereotype.Component

const val VALID_CARD_ENDING = "4401"
@Component
class CardValidator {
    fun validate(cardDto : CardDto) : Boolean = cardDto.number?.endsWith(VALID_CARD_ENDING) ?: false

}