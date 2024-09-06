package com.desafio.san_giorgio_api.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.extension.LocalStackExtension;

@SpringBootTest
@ExtendWith(LocalStackExtension.class)
class PaymentGatewayTests {
    @Autowired
    PaymentGateway paymentGateway;

    @ParameterizedTest
    @CsvSource({
        "122.8725, 62.1968, 517.1569, true",
        "360.0913, 988.6076, 862.337, false"
    })
    void given_payments_when_check_if_payment_was_partial_should_return_expected_value(String receivedValue, String amountPaid, String totalValue, boolean expectedResult) {
        PaymentItemModel paymentReceived = PaymentItemModel.builder()
            .paymentValue(new BigDecimal(receivedValue))
            .build();
        PaymentItemModel paymentStored = PaymentItemModel.builder()
            .amountPaid(new BigDecimal(amountPaid))
            .paymentValue(new BigDecimal(totalValue))
            .build();

        assertEquals(expectedResult, paymentGateway.checkIfPaymentWasPartial(paymentReceived, paymentStored));
    }

    @ParameterizedTest
    @CsvSource({
        "559.1246, 206.1167, 765.2413, true",
        "24.032, 312.4382, 341.2768, false"
    })
    void given_payments_when_check_if_payment_was_total_should_return_expected_value(String receivedValue, String amountPaid, String totalValue, boolean expectedResult) {
        PaymentItemModel paymentReceived = PaymentItemModel.builder()
            .paymentValue(new BigDecimal(receivedValue))
            .build();
        PaymentItemModel paymentStored = PaymentItemModel.builder()
            .amountPaid(new BigDecimal(amountPaid))
            .paymentValue(new BigDecimal(totalValue))
            .build();

        assertEquals(expectedResult, paymentGateway.checkIfPaymentWasTotal(paymentReceived, paymentStored));
    }

    @ParameterizedTest
    @CsvSource({
        "780.3546, 827.1204, 500.2875, true",
        "88.5655, 87.0163, 229.6853, false"
    })
    void given_payments_when_check_if_payment_was_excedeed_should_return_expected_value(String receivedValue, String amountPaid, String totalValue, boolean expectedResult) {
        PaymentItemModel paymentReceived = PaymentItemModel.builder()
            .paymentValue(new BigDecimal(receivedValue))
            .build();
        PaymentItemModel paymentStored = PaymentItemModel.builder()
            .amountPaid(new BigDecimal(amountPaid))
            .paymentValue(new BigDecimal(totalValue))
            .build();

        assertEquals(expectedResult, paymentGateway.checkIfPaymentWasExcedeed(paymentReceived, paymentStored));
    }

    @ParameterizedTest
    @CsvSource({
        "352.6546",
        "12.7992",
        "753.5227"
    })
    void given_payment_when_update_partial_amount_paid_should_apply_changes(String amountPaid) {
        String paymentId = "052d3e66-f9ac-478f-9121-c894ac429a17";
        paymentGateway.updatePartialAmountPaid(paymentId, new BigDecimal(amountPaid));

        Optional<PaymentItemModel> paymentData = paymentGateway.findPayment(paymentId);

        assertTrue(paymentData.isPresent());
        assertEquals(new BigDecimal(amountPaid).setScale(2, RoundingMode.HALF_UP), paymentData.get().getAmountPaid());
        assertEquals("PARTIAL", paymentData.get().getPaymentStatus());
    }

    @ParameterizedTest
    @CsvSource({
        "911.2847",
        "315.6197",
        "613.0221"
    })
    void given_payment_when_update_total_amount_paid_should_apply_changes(String amountPaid) {
        String paymentId = "a7161c19-ae37-4231-90b7-87dbdc32288c";
        paymentGateway.updateTotalAmountPaid(paymentId, new BigDecimal(amountPaid));

        Optional<PaymentItemModel> paymentData = paymentGateway.findPayment(paymentId);

        assertTrue(paymentData.isPresent());
        assertEquals(new BigDecimal(amountPaid).setScale(2, RoundingMode.HALF_UP), paymentData.get().getAmountPaid());
        assertEquals("TOTAL", paymentData.get().getPaymentStatus());
    }

    
    @ParameterizedTest
    @CsvSource({
        "581.5755",
        "572.1106",
        "644.8726"
    })
    void given_payment_when_update_excedeed_amount_paid_should_apply_changes(String amountPaid) {
        String paymentId = "052d3e66-f9ac-478f-9121-c894ac429a17";
        paymentGateway.updateExcedeedAmountPaid(paymentId, new BigDecimal(amountPaid));

        Optional<PaymentItemModel> paymentData = paymentGateway.findPayment(paymentId);

        assertTrue(paymentData.isPresent());
        assertEquals(new BigDecimal(amountPaid).setScale(2, RoundingMode.HALF_UP), paymentData.get().getAmountPaid());
        assertEquals("EXCEDEED", paymentData.get().getPaymentStatus());
    }
}
