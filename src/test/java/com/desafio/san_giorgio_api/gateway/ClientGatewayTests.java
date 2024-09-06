package com.desafio.san_giorgio_api.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.domain.model.PaymentModel;
import com.desafio.san_giorgio_api.extension.LocalStackExtension;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(LocalStackExtension.class)
class ClientGatewayTests {
    @Autowired
    ClientGateway clientGateway;

    @ParameterizedTest
    @CsvSource({
        "5dc12ebf-c438-4012-b098-fbf289cc9279, true",
        "0bd81ec6-81f9-48f0-9a93-98e808cf6794, false"
    })
    void given_client_id_when_check_if_client_exists_should_return_expected_result(String clientId, boolean expectedResult) {
        boolean result = clientGateway.checkIfClientExists(clientId);
        assertEquals(expectedResult, result);
    }

    @Transactional
    @ParameterizedTest
    @CsvSource({
        "080ee758-af2e-496d-97b4-d2344a821819, a7161c19-ae37-4231-90b7-87dbdc32288c, 2, PARTIAL",
        "ae14e23a-4104-455c-bfe9-2720d9e5e75b, f055f7d8-cf87-4d5e-ac86-ce18772788b8, 1, TOTAL"
    })
    void given_client_id_when_find_client_information_should_return_expected_client(String clientId, String expectedPaymentId, Integer paymentsSize, String expectedStatus) {
        PaymentModel client = clientGateway.findClientInformation(clientId);

        assertEquals(clientId, client.getClientId());
        assertEquals(paymentsSize, client.getPaymentItems().size());
        assertEquals(expectedPaymentId, client.getPaymentItems().get(0).getPaymentId());
        assertEquals(expectedStatus, client.getPaymentItems().get(0).getPaymentStatus());
    }

    @Transactional
    @ParameterizedTest
    @CsvSource({
        "77782320-356b-4a5b-b55f-1df71e0a8c47, df5239dd-f0af-4331-8492-50305b3ec913, 100.00, PARTIAL",
        "c8bfde39-3895-43ab-8736-a2ab7c43ea7b, 30add840-09bb-4718-9ffe-42132dbcc495, 50.00, TOTAL",
        "49f0592c-6e70-4b64-9ee8-3bd127e9e5a8, 8bf061c4-4ec0-43d3-9409-a05290ee1892, 100.00, EXCEDEED",
    })
    void given_client_when_prepare_client_with_new_information_should_return_expected_result(String clientId, String paymentId, String amountPaid, String statusExpected) {
        PaymentItemModel payment = PaymentItemModel.builder()
            .paymentId(paymentId)
            .paymentValue(new BigDecimal(amountPaid))
            .build();

        PaymentModel client = PaymentModel.builder()
            .clientId(clientId)
            .paymentItems(List.of(payment))
            .build();

        PaymentModel result = clientGateway.prepareClientWithNewInformation(client);

        assertEquals(clientId, result.getClientId());
        assertEquals(paymentId, result.getPaymentItems().get(0).getPaymentId());
        assertEquals(statusExpected, result.getPaymentItems().get(0).getPaymentStatus());
    }
}
