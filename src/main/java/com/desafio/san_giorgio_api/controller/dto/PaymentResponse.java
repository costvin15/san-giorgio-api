package com.desafio.san_giorgio_api.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("payment_items")
    private List<PaymentItemResponse> paymentItems;
}
