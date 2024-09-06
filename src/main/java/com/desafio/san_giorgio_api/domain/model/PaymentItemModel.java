package com.desafio.san_giorgio_api.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentItemModel {
    private String paymentId;
    private BigDecimal amountPaid;
    private BigDecimal paymentValue;
    private String paymentStatus;
}
