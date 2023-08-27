package com.example.inbank.loan.dto

data class LoanResponse(
    val approved: Boolean, val maxAmount: Double?
) {
    companion object {
        val notAllowed = LoanResponse(false, null)
    }
}