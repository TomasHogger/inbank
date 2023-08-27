package com.example.inbank.user_account.service

import com.example.inbank.user_account.domain.UserAccountSegment

interface UserAccountService {
    fun isUserAccountHasDebt(personalCode: String): Boolean
    fun getUserAccountSegment(personalCode: String): UserAccountSegment
}