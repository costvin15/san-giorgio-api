package com.desafio.san_giorgio_api.controller.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentItemResponse {
    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("payment_value")
    private BigDecimal paymentValue;

    @JsonProperty("payment_status")
    private String paymentStatus;
}
