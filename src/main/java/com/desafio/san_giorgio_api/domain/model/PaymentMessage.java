package com.desafio.san_giorgio_api.domain.model;

import java.math.BigDecimal;

public record PaymentMessage (String paymentId, BigDecimal paymentValue) {}
