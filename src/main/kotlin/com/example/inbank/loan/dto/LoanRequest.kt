package com.example.inbank.loan.dto

data class LoanRequest(
    val personalCode: String,
    val loanAmount: Int,
    val monthPeriod: Int
)
