package com.frizzer.frontend.model
enum class StatusCode(val message : String, val code: Int) {
    OK("OK",0),
    INVALID_PHONE("You phone is invalid", 1),
    INVALID_PASSWORD("You password is invalid",2),
    NOT_FOUND("Client not found",3)
}