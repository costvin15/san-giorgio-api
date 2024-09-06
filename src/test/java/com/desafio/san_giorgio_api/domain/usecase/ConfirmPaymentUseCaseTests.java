package com.desafio.san_giorgio_api.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.desafio.san_giorgio_api.config.errors.ClientNotFoundException;
import com.desafio.san_giorgio_api.config.errors.PaymentNotFoundException;
import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.domain.model.PaymentModel;
import com.desafio.san_giorgio_api.extension.LocalStackExtension;

import jakarta.transaction.Transactional;

@Testcontainers
@SpringBootTest
@ExtendWith(LocalStackExtension.class)
class ConfirmPaymentUseCaseTests {
    @Autowired
    ConfirmPaymentUseCase confirmPayment;

    @Test
    void given_invalid_client_id_when_confirm_should_throw_expected_exception() {
        PaymentModel paymentModel = new PaymentModel("non-existent", List.of());
        Exception exception =  assertThrows(ClientNotFoundException.class, () -> confirmPayment.confirm(paymentModel));
        assertEquals("Client non-existent not found.", exception.getMessage());
    }

    @Test
    void given_invalid_payment_ids_when_confirm_should_throw_excepted_exception() {
        PaymentModel paymentModel = new PaymentModel("5dc12ebf-c438-4012-b098-fbf289cc9279", List.of(
            new PaymentItemModel("invalid-payment-1", BigDecimal.ZERO, BigDecimal.ZERO, "PARTIAL"),
            new PaymentItemModel("invalid-payment-2", BigDecimal.ZERO, BigDecimal.ZERO, "PARTIAL"),
            new PaymentItemModel("invalid-payment-3", BigDecimal.ZERO, BigDecimal.ZERO, "PARTIAL")
        ));
        Exception exception = assertThrows(PaymentNotFoundException.class, () -> confirmPayment.confirm(paymentModel));
        assertEquals("Payments invalid-payment-1, invalid-payment-2, invalid-payment-3 not found.", exception.getMessage());
    }

    @Transactional
    @ParameterizedTest
    @CsvSource({
        "5dc12ebf-c438-4012-b098-fbf289cc9279, 94f3f77c-0907-4d14-bf29-0d91dfc2353a, 1, 75.00, 75.00, PARTIAL",
        "5dc12ebf-c438-4012-b098-fbf289cc9279, 052d3e66-f9ac-478f-9121-c894ac429a17, 0, 100.00, 100.00, TOTAL",
        "49f0592c-6e70-4b64-9ee8-3bd127e9e5a8, 8bf061c4-4ec0-43d3-9409-a05290ee1892, 0, 100.00, 200.00, EXCEDEED",
    })
    void given_client_with_payments_when_confirm_should_return_desired_data(String clientId, String paymentId, Integer paymentIndex, String amountPaid, String expectedPaidValue, String expectedStatus) {
        PaymentItemModel paymentItem = PaymentItemModel.builder()
            .paymentId(paymentId)
            .paymentValue(new BigDecimal(amountPaid))
            .build();
        PaymentModel paymentRequest = PaymentModel.builder()
            .clientId(clientId)
            .paymentItems(List.of(paymentItem))
            .build();

        PaymentModel result = confirmPayment.confirm(paymentRequest);
        assertEquals(clientId, result.getClientId());
        assertEquals(2, result.getPaymentItems().size());
        assertEquals(paymentId, result.getPaymentItems().get(paymentIndex).getPaymentId());
        assertEquals(new BigDecimal(expectedPaidValue), result.getPaymentItems().get(paymentIndex).getAmountPaid());
        assertEquals(expectedStatus, result.getPaymentItems().get(paymentIndex).getPaymentStatus());
    }
}
