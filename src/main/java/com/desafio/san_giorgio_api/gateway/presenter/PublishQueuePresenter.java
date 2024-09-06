package com.desafio.san_giorgio_api.gateway.presenter;

import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.domain.model.PaymentMessage;

public class PublishQueuePresenter {
    public static PaymentMessage toDomain(PaymentItemModel payment) {
        return new PaymentMessage(payment.getPaymentId(), payment.getPaymentValue());
    }
}
