package com.desafio.san_giorgio_api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.desafio.san_giorgio_api.config.errors.ErrorMessage;
import com.desafio.san_giorgio_api.controller.dto.PaymentItemRequest;
import com.desafio.san_giorgio_api.controller.dto.PaymentRequest;
import com.desafio.san_giorgio_api.controller.dto.PaymentResponse;
import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.domain.model.PaymentModel;
import com.desafio.san_giorgio_api.domain.usecase.ConfirmPaymentUseCase;
import com.desafio.san_giorgio_api.extension.LocalStackExtension;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = PaymentController.class)
@AutoConfigureMockMvc
@ExtendWith(LocalStackExtension.class)
class PaymentControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ConfirmPaymentUseCase confirmPayment;

    @Test
    void given_empty_request_when_calling_payment_route_should_throw_expected_errors() throws Exception {
        PaymentRequest request = PaymentRequest.builder()
            .build();

        MvcResult result = mockMvc.perform(put("/api/payment")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn();

        String json = result.getResponse().getContentAsString();
        ErrorMessage errorMessage = objectMapper.readValue(json, ErrorMessage.class);

        assertEquals(HttpStatus.BAD_REQUEST, errorMessage.httpStatus);
        assertEquals(4, errorMessage.getErrors().size());
        assertTrue(errorMessage.getErrors().contains("Payment list is mandatory"));
        assertTrue(errorMessage.getErrors().contains("Payment list is mandatory"));
        assertTrue(errorMessage.getErrors().contains("Payment list must not be empty"));
        assertTrue(errorMessage.getErrors().contains("Client id is mandatory"));
    }

    @Test
    void given_partial_request_when_calling_payment_route_should_store_expected_data() throws JsonProcessingException, Exception {
        String clientId = "5dc12ebf-c438-4012-b098-fbf289cc9279";
        String paymentId = "052d3e66-f9ac-478f-9121-c894ac429a17";
        BigDecimal amountPaid = new BigDecimal("135.5026");

        PaymentItemModel mockedPayment = PaymentItemModel.builder()
            .paymentId(paymentId)
            .paymentStatus("TOTAL")
            .amountPaid(amountPaid)
            .build();

        PaymentModel mockedResponse = PaymentModel.builder()
            .clientId(clientId)
            .paymentItems(List.of(mockedPayment))
            .build();

        when(confirmPayment.confirm(any(PaymentModel.class)))
            .thenReturn(mockedResponse);

        PaymentItemRequest paymentRequest = PaymentItemRequest.builder()
            .paymentId(paymentId)
            .paymentValue(amountPaid)
            .build();

        PaymentRequest request = PaymentRequest.builder()
            .clientId(clientId)
            .paymentItems(List.of(paymentRequest))
            .build();

        MvcResult result = mockMvc.perform(put("/api/payment")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andReturn();

        String json = result.getResponse().getContentAsString();
        PaymentResponse response = objectMapper.readValue(json, PaymentResponse.class);

        assertEquals(clientId, response.getClientId());
        assertEquals(1, response.getPaymentItems().size());
        assertEquals(paymentId, response.getPaymentItems().get(0).getPaymentId());
        assertEquals("TOTAL", response.getPaymentItems().get(0).getPaymentStatus());
        assertEquals(amountPaid, response.getPaymentItems().get(0).getPaymentValue());
    }
}
