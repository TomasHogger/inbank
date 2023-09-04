package com.example.inbank.loan.service

import com.example.inbank.loan.dto.LoanRequest
import com.example.inbank.loan.dto.LoanResponse
import com.example.inbank.loan.exception.InvalidLoanAmountException
import com.example.inbank.loan.exception.InvalidMonthPeriodException
import com.example.inbank.user_account.service.UserAccountService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

const val MIN_LOAN_AMOUNT = 2_000.0
const val MAX_LOAN_AMOUNT = 10_000.0
const val MIN_MONTH_AMOUNT = 12
const val MAX_MONTH_AMOUNT = 60

@Service
class LoanServiceImpl(
    private val userAccountService: UserAccountService, private val loanModifierService: LoanModifierService
) : LoanService {

    override fun checkLoanAvailability(loanRequest: LoanRequest): LoanResponse {
        if (loanRequest.loanAmount < MIN_LOAN_AMOUNT || loanRequest.loanAmount > MAX_LOAN_AMOUNT) {
            throw InvalidLoanAmountException()
        }
        if (loanRequest.monthPeriod < MIN_MONTH_AMOUNT || loanRequest.monthPeriod > MAX_MONTH_AMOUNT) {
            throw InvalidMonthPeriodException()
        }

        if (userAccountService.isUserAccountHasDebt(loanRequest.personalCode)) {
            return LoanResponse.notAllowed
        }

        val modifierScore = BigDecimal(loanModifierService.getModifierScore(loanRequest.personalCode))
        val loanScore = (modifierScore.divide(
            BigDecimal(loanRequest.loanAmount),
            2,
            RoundingMode.HALF_UP
        )) * BigDecimal(loanRequest.monthPeriod)
        val loanMaxAmount = (modifierScore * BigDecimal(loanRequest.monthPeriod)).toDouble().let {
            if (it > MAX_LOAN_AMOUNT) {
                return@let MAX_LOAN_AMOUNT
            } else if (it < MIN_LOAN_AMOUNT) {
                return@let MIN_LOAN_AMOUNT
            }
            return@let it
        }

        return LoanResponse(loanScore >= BigDecimal.ONE, loanMaxAmount)
    }
}
