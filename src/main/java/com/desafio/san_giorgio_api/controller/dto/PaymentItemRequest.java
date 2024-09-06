package com.desafio.san_giorgio_api.controller.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentItemRequest {
    @JsonProperty("payment_id")
    @NotNull(message = "Payment id is mandatory")
    @NotBlank(message = "Payment id must not be blank")
    private String paymentId;

    @JsonProperty("payment_value")
    @NotNull(message = "Payment value is mandatory")
    private BigDecimal paymentValue;
}
