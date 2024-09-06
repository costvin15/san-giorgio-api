package com.desafio.san_giorgio_api.domain.usecase;

import static org.junit.Assert.assertEquals;

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
class ProcessPartialPaymentUseCaseTests {
    @Autowired
    ProcessPartialPaymentUseCase processPartialPayment;

    @Transactional
    @ParameterizedTest
    @CsvSource({
        "8cb91c5e-fbe6-4b63-86bb-2bfb71eca342, true",
        "3e4b438a-7306-46c3-9252-75d32665cec7, false"
    })
    void given_payment_id_when_confirm_should_return_expected_result(String paymentId, boolean expectedResult) {
        PaymentMessage paymentMessage = new PaymentMessage(paymentId, BigDecimal.ZERO);
        boolean result = processPartialPayment.confirm(paymentMessage);
        assertEquals(result, expectedResult);
    }
}
