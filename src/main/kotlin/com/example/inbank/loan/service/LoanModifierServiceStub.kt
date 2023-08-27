package com.example.inbank.loan.service

import com.example.inbank.user_account.domain.UserAccountSegment
import com.example.inbank.user_account.service.UserAccountService
import org.springframework.stereotype.Service

@Service
class LoanModifierServiceStub(
    private val userAccountService: UserAccountService
) : LoanModifierService {
    override fun getModifierScore(personalCode: String) =
        when (userAccountService.getUserAccountSegment(personalCode)) {
            UserAccountSegment.FIRST_SEGMENT -> 100.0
            UserAccountSegment.SECOND_SEGMENT -> 300.0
            UserAccountSegment.THIRD_SEGMENT -> 1000.0
        }
}