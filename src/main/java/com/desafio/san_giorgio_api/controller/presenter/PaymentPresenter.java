package com.desafio.san_giorgio_api.controller.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.desafio.san_giorgio_api.controller.dto.PaymentItemResponse;
import com.desafio.san_giorgio_api.controller.dto.PaymentRequest;
import com.desafio.san_giorgio_api.controller.dto.PaymentResponse;
import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.domain.model.PaymentModel;

public class PaymentPresenter {
    public static PaymentModel toDomain(PaymentRequest request) {
        List<PaymentItemModel> items = new ArrayList<>();
        request.getPaymentItems().forEach(payment -> {
            var item = PaymentItemModel.builder()
                .paymentId(payment.getPaymentId())
                .paymentValue(payment.getPaymentValue())
                .build();
            items.add(item);
        });

        return PaymentModel.builder()
            .clientId(request.getClientId())
            .paymentItems(items)
            .build();
    }

    public static PaymentResponse toDomain(PaymentModel payment) {
        List<PaymentItemResponse> items = payment.getPaymentItems().stream()
            .map(item -> {
                return PaymentItemResponse.builder()
                    .paymentId(item.getPaymentId())
                    .paymentStatus(item.getPaymentStatus())
                    .paymentValue(item.getAmountPaid())
                    .build();
            })
            .collect(Collectors.toList());

        return PaymentResponse.builder()
            .clientId(payment.getClientId())
            .paymentItems(items)
            .build();
    }
}
