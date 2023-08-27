package com.example.inbank.loan.controller

import com.example.inbank.loan.dto.LoanRequest
import com.example.inbank.loan.exception.InvalidLoanAmountException
import com.example.inbank.loan.service.LoanService
import com.example.inbank.user_account.exception.UserAccountNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class LoanController(
    val loanService: LoanService
) {
    @PostMapping("check_loan_availability")
    fun checkLoanAvailability(@RequestBody loanRequest: LoanRequest) = loanService.checkLoanAvailability(loanRequest)

    @ExceptionHandler(
        InvalidLoanAmountException::class,
        InvalidLoanAmountException::class,
        UserAccountNotFoundException::class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequest(ex: Exception): ResponseEntity<String> {
        LoggerFactory.getLogger(javaClass).error(ex.message, ex)
        return ResponseEntity.badRequest().body(ex.message)
    }
}