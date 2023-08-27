package com.example.inbank.loan.service

interface LoanModifierService {
    fun getModifierScore(personalCode: String): Double
}