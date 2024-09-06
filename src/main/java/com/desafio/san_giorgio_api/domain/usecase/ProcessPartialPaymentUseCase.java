package com.desafio.san_giorgio_api.domain.usecase;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.domain.model.PaymentMessage;
import com.desafio.san_giorgio_api.gateway.PaymentGateway;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProcessPartialPaymentUseCase {
    PaymentGateway paymentGateway;

    public boolean confirm(PaymentMessage paymentMessage) {
        Optional<PaymentItemModel> paymentInformation = paymentGateway.findPayment(paymentMessage.paymentId());
        if (!paymentInformation.isPresent()) {
            return false;
        }

        BigDecimal amountPaid = paymentInformation.get().getAmountPaid()
            .add(paymentMessage.paymentValue());
        paymentGateway.updatePartialAmountPaid(paymentMessage.paymentId(), amountPaid);
        return true;
    }
}
