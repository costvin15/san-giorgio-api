package com.desafio.san_giorgio_api.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.desafio.san_giorgio_api.domain.model.PaymentMessage;
import com.desafio.san_giorgio_api.extension.LocalStackExtension;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(LocalStackExtension.class)
class ProcessTotalPaymentUseCaseTests {
    @Autowired
    ProcessTotalPaymentUseCase processTotalPayment;

    @Transactional
    @ParameterizedTest
    @CsvSource({
        "df5239dd-f0af-4331-8492-50305b3ec913, true",
        "65384d6e-54f6-4141-b539-84350ffe1e72, false"
    })
    void given_payment_id_when_confirm_should_return_expected_result(String paymentId, boolean expectedResult) {
        PaymentMessage paymentMessage = new PaymentMessage(paymentId, BigDecimal.ZERO);
        boolean result = processTotalPayment.confirm(paymentMessage);
        assertEquals(result, expectedResult);
    }
}
