package com.desafio.san_giorgio_api.gateway.presenter;

import java.util.ArrayList;
import java.util.List;

import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.domain.model.PaymentModel;
import com.desafio.san_giorgio_api.repository.entities.ClientEntity;

public class ClientPresenter {
    public static PaymentModel toDomain(ClientEntity entity) {
        List<PaymentItemModel> items = new ArrayList<>();
        entity.getPayments().forEach(payment -> {
            var item = PaymentItemModel.builder()
                .paymentId(payment.getPaymentId())
                .amountPaid(payment.getAmountPaid())
                .paymentValue(payment.getPaymentValue())
                .paymentStatus(payment.getPaymentStatus().name())
                .build();
            items.add(item);
        });
        return PaymentModel.builder()
            .clientId(entity.getClientId())
            .paymentItems(items)
            .build();
    }
}
