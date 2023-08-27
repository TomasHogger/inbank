package com.example.inbank.loan.controller

import com.example.inbank.loan.dto.LoanRequest
import com.example.inbank.loan.service.LoanService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoanController(
    val loanService: LoanService
) {
    @PostMapping("check_loan_availability")
    fun checkLoanAvailability(@RequestBody loanRequest: LoanRequest) = loanService.checkLoanAvailability(loanRequest)
}