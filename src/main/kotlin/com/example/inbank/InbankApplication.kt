package com.example.inbank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InbankApplication

fun main(args: Array<String>) {
    runApplication<InbankApplication>(*args)
}
