package com.desafio.san_giorgio_api.gateway.presenter;

import java.util.Optional;

import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.repository.entities.PaymentEntity;

public class PaymentPresenter {
    public static Optional<PaymentItemModel> toDomain(Optional<PaymentEntity> entity) {
        if (!entity.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(PaymentItemModel.builder()
            .paymentId(entity.get().getPaymentId())
            .amountPaid(entity.get().getAmountPaid())
            .paymentValue(entity.get().getPaymentValue())
            .paymentStatus(entity.get().getPaymentStatus().name())
            .build());
    }
}
