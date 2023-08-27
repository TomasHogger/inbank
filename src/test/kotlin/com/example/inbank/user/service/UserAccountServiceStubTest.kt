package com.example.inbank.user.service

import com.example.inbank.user_account.domain.UserAccountSegment
import com.example.inbank.user_account.exception.UserAccountNotFoundException
import com.example.inbank.user_account.service.UserAccountServiceStub
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class UserAccountServiceStubTest {

    private val userAccountService = UserAccountServiceStub()

    @ParameterizedTest
    @CsvSource(
        textBlock = """
        49002010965,true
        49002010976,false
        49002010987,false
        49002010998,false"""
    )
    fun isUserAccountHasDebt__known_accounts(personalCode: String, result: Boolean) {
        Assertions.assertEquals(result, userAccountService.isUserAccountHasDebt(personalCode))
    }

    @Test
    fun isUserAccountHasDebt__unknown_accounts() {
        Assertions.assertThrows(
            UserAccountNotFoundException::class.java
        ) {
            userAccountService.isUserAccountHasDebt("123412341234")
        }
    }

    @ParameterizedTest
    @CsvSource(
        textBlock = """
        49002010965,FIRST_SEGMENT
        49002010976,FIRST_SEGMENT
        49002010987,SECOND_SEGMENT
        49002010998,THIRD_SEGMENT"""
    )
    fun getUserAccountSegment__known_accounts(personalCode: String, result: UserAccountSegment) {
        Assertions.assertEquals(result, userAccountService.getUserAccountSegment(personalCode))
    }

    @Test
    fun getUserAccountSegment__unknown_accounts() {
        Assertions.assertThrows(
            UserAccountNotFoundException::class.java
        ) {
            userAccountService.getUserAccountSegment("123412341234")
        }
    }
}