package com.desafio.san_giorgio_api.config.errors;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(List<String> paymentsIds) {
        super(String.format("Payments %s not found.", paymentsIds.stream().collect(Collectors.joining(", "))));
    }
}
