package com.example.inbank.loan.service

import com.example.inbank.loan.dto.LoanRequest
import com.example.inbank.loan.dto.LoanResponse
import com.example.inbank.loan.exception.InvalidLoanAmountException
import com.example.inbank.loan.exception.InvalidMonthPeriodException
import com.example.inbank.user_account.service.UserAccountService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class LoanServiceImpl(
    private val userAccountService: UserAccountService, private val loanModifierService: LoanModifierService
) : LoanService {
    override fun checkLoanAvailability(loanRequest: LoanRequest): LoanResponse {
        if (loanRequest.loanAmount < 2_000 || loanRequest.loanAmount > 10_000) {
            throw InvalidLoanAmountException()
        }
        if (loanRequest.monthPeriod < 12 || loanRequest.monthPeriod > 60) {
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
        var loanMaxAmount: Double? = (modifierScore * BigDecimal(loanRequest.monthPeriod)).toDouble()

        if (loanMaxAmount!! > 10_000.0) {
            loanMaxAmount = 10_000.0
        } else if (loanMaxAmount < 2_000.0) {
            loanMaxAmount = null;
        }

        return LoanResponse(loanScore >= BigDecimal.ONE, loanMaxAmount)
    }
}
