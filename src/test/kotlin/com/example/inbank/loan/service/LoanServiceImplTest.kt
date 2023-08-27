package com.example.inbank.loan.service

import com.example.inbank.loan.dto.LoanRequest
import com.example.inbank.loan.dto.LoanResponse
import com.example.inbank.loan.exception.InvalidLoanAmountException
import com.example.inbank.loan.exception.InvalidMonthPeriodException
import com.example.inbank.user_account.domain.UserAccountSegment
import com.example.inbank.user_account.service.UserAccountService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class LoanServiceImplTest {

    @Test
    fun checkLoanAvailability__user_has_debt() {
        val userAccountService = object : UserAccountService {
            override fun isUserAccountHasDebt(personalCode: String) = true

            override fun getUserAccountSegment(personalCode: String): UserAccountSegment {
                TODO("Not yet implemented")
            }
        }
        val loanModifierService = object : LoanModifierService {
            override fun getModifierScore(personalCode: String) = 300.0
        }

        Assertions.assertEquals(
            LoanResponse.notAllowed,
            LoanServiceImpl(userAccountService, loanModifierService).checkLoanAvailability(
                LoanRequest(
                    "123412341234",
                    2000,
                    12
                )
            )
        )
    }

    @Test
    fun checkLoanAvailability__invalid_loan_amount() {
        val userAccountService = object : UserAccountService {
            override fun isUserAccountHasDebt(personalCode: String) = false

            override fun getUserAccountSegment(personalCode: String): UserAccountSegment {
                TODO("Not yet implemented")
            }
        }
        val loanModifierService = object : LoanModifierService {
            override fun getModifierScore(personalCode: String) = 300.0
        }

        Assertions.assertThrows(InvalidLoanAmountException::class.java) {
            LoanServiceImpl(userAccountService, loanModifierService).checkLoanAvailability(
                LoanRequest(
                    "123412341234",
                    1000,
                    12
                )
            )
        }
    }

    @Test
    fun checkLoanAvailability__invalid_month_period() {
        val userAccountService = object : UserAccountService {
            override fun isUserAccountHasDebt(personalCode: String) = false

            override fun getUserAccountSegment(personalCode: String): UserAccountSegment {
                TODO("Not yet implemented")
            }
        }
        val loanModifierService = object : LoanModifierService {
            override fun getModifierScore(personalCode: String) = 300.0
        }

        Assertions.assertThrows(InvalidMonthPeriodException::class.java) {
            LoanServiceImpl(userAccountService, loanModifierService).checkLoanAvailability(
                LoanRequest(
                    "123412341234",
                    2000,
                    1
                )
            )
        }
    }

    @ParameterizedTest
    @CsvSource(
        textBlock = """
        100,4000,12,false,
        300,4000,12,false,3600
        300,4000,14,true,4200
        300,6000,40,true,10000"""
    )
    fun checkLoanAvailability(
        loanModifier: Double,
        loanAmount: Int,
        monthPeriod: Int,
        approved: Boolean,
        maxAmount: Double?
    ) {
        val userAccountService = object : UserAccountService {
            override fun isUserAccountHasDebt(personalCode: String) = false

            override fun getUserAccountSegment(personalCode: String): UserAccountSegment {
                TODO("Not yet implemented")
            }
        }
        val loanModifierService = object : LoanModifierService {
            override fun getModifierScore(personalCode: String) = loanModifier
        }

        Assertions.assertEquals(
            LoanResponse(
                approved,
                maxAmount
            ),
            LoanServiceImpl(userAccountService, loanModifierService).checkLoanAvailability(
                LoanRequest(
                    "123412341234",
                    loanAmount,
                    monthPeriod
                )
            )
        )
    }
}