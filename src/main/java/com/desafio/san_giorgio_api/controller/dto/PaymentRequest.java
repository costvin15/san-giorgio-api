package com.desafio.san_giorgio_api.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    @JsonProperty("client_id")
    @NotNull(message = "Client id is mandatory")
    @NotBlank(message = "Client id must not be blank")
    private String clientId;

    @Valid
    @JsonProperty("payment_items")
    @NotNull(message = "Payment list is mandatory")
    @NotEmpty(message = "Payment list must not be empty")
    private List<PaymentItemRequest> paymentItems;
}
