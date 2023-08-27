package com.example.inbank.loan.controller

import com.example.inbank.MockitoHelper
import com.example.inbank.loan.dto.LoanResponse
import com.example.inbank.loan.service.LoanService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@WebMvcTest
class LoanControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @MockBean
    lateinit var loanService: LoanService

    @BeforeEach
    fun setUp() {
        Mockito
            .`when`(loanService.checkLoanAvailability(MockitoHelper.anyObject()))
            .thenReturn(LoanResponse.notAllowed)
    }

    @Test
    fun checkLoanAvailability() {
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/check_loan_availability")
                    .content(
                        """
                        {
                            "personalCode": "1234",
                            "loanAmount": 4000,
                            "monthPeriod": 15
                        }
                        """.trimIndent()
                    )
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}