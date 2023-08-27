package com.example.inbank.controller_advice

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ControllerAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun badRequest(ex: Exception): ResponseEntity<String> {
        LoggerFactory.getLogger(javaClass).error(ex.message, ex)
        return ResponseEntity.internalServerError().body(ex.message)
    }
}