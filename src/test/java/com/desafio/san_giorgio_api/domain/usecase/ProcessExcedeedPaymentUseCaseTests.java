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
class ProcessExcedeedPaymentUseCaseTests {
    @Autowired
    ProcessExcedeedPaymentUseCase processExcedeedPayment;

    @Transactional
    @ParameterizedTest
    @CsvSource({
        "f4a4c673-6bfc-49af-8cbf-71492dce1d9b, true",
        "c560735f-1774-4972-82d1-ad8ec0f18c35, false"
    })
    void given_payment_id_when_confirm_should_return_expected_result(String paymentId, boolean expectedResult) {
        PaymentMessage paymentMessage = new PaymentMessage(paymentId, BigDecimal.ZERO);
        boolean result = processExcedeedPayment.confirm(paymentMessage);
        assertEquals(result, expectedResult);
    }
}
