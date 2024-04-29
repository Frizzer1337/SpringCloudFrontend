package com.frizzer.frontend.model
enum class StatusCode(val message : String, val code: Int) {
    OK("OK",200),
    PHONE_OK("PHONE_OK",201) ,
    INVALID_PHONE("You phone is invalid", 400),
    INVALID_PASSWORD("You password is invalid",401),
    NOT_FOUND("Client not found",404),
    INTERNAL_ERROR("Internal error",500)
}