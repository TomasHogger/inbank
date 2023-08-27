package com.example.inbank.loan.service

import com.example.inbank.user_account.domain.UserAccountSegment
import com.example.inbank.user_account.service.UserAccountService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class LoanModifierServiceStubTest {
    @ParameterizedTest
    @CsvSource(
        textBlock = """
        FIRST_SEGMENT,100
        SECOND_SEGMENT,300
        THIRD_SEGMENT,1000"""
    )
    fun getModifierScore(segment: UserAccountSegment, result: Double) {
        val userAccountService = object : UserAccountService {
            override fun isUserAccountHasDebt(personalCode: String): Boolean {
                TODO("Not yet implemented")
            }

            override fun getUserAccountSegment(personalCode: String) = segment
        }
        Assertions.assertEquals(result, LoanModifierServiceStub(userAccountService).getModifierScore("1234"))
    }
}