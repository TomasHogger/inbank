package com.example.inbank.user_account.service

import com.example.inbank.user_account.domain.UserAccountSegment
import com.example.inbank.user_account.exception.UserAccountNotFoundException
import org.springframework.stereotype.Service

@Service
class UserAccountServiceStub : UserAccountService {
    override fun isUserAccountHasDebt(personalCode: String): Boolean {
        return when (personalCode) {
            "49002010965" -> true
            "49002010976", "49002010987", "49002010998" -> false
            else -> throw UserAccountNotFoundException(personalCode)
        }
    }

    override fun getUserAccountSegment(personalCode: String): UserAccountSegment {
        return when (personalCode) {
            "49002010965", "49002010976" -> UserAccountSegment.FIRST_SEGMENT
            "49002010987" -> UserAccountSegment.SECOND_SEGMENT
            "49002010998" -> UserAccountSegment.THIRD_SEGMENT
            else -> throw UserAccountNotFoundException(personalCode)
        }
    }
}