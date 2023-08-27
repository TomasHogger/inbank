package com.example.inbank.loan.service

import com.example.inbank.loan.dto.LoanRequest
import com.example.inbank.loan.dto.LoanResponse

interface LoanService {
    fun checkLoanAvailability(loanRequest: LoanRequest): LoanResponse
}