package com.desafio.san_giorgio_api.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentModel {
    private String clientId;
    private List<PaymentItemModel> paymentItems;
}
