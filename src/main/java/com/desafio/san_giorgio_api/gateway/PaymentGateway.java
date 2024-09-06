package com.desafio.san_giorgio_api.gateway;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.gateway.presenter.PaymentPresenter;
import com.desafio.san_giorgio_api.repository.PaymentRepository;
import com.desafio.san_giorgio_api.repository.entities.PaymentStatus;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentGateway {
    PaymentRepository paymentRepository;

    public Optional<PaymentItemModel> findPayment(String paymentId) {
        return PaymentPresenter.toDomain(this.paymentRepository.findById(paymentId));
    }

    public boolean checkIfPaymentWasPartial(PaymentItemModel paymentReceived, PaymentItemModel paymentInformation) {
        BigDecimal totalPaid = paymentInformation.getAmountPaid().add(paymentReceived.getPaymentValue());
        return paymentInformation.getPaymentValue().compareTo(totalPaid) == 1;
    }

    public boolean checkIfPaymentWasTotal(PaymentItemModel paymentReceived, PaymentItemModel paymentInformation) {
        BigDecimal totalPaid = paymentInformation.getAmountPaid().add(paymentReceived.getPaymentValue());
        return paymentInformation.getPaymentValue().compareTo(totalPaid) == 0;
    }

    public boolean checkIfPaymentWasExcedeed(PaymentItemModel paymentReceived, PaymentItemModel paymentInformation) {
        BigDecimal totalPaid = paymentInformation.getAmountPaid().add(paymentReceived.getPaymentValue());
        return paymentInformation.getPaymentValue().compareTo(totalPaid) == -1;
    }

    public void updatePartialAmountPaid(String paymentId, BigDecimal amountPaid) {
        this.updateAmounPaidAndPaymentStatus(paymentId, amountPaid, PaymentStatus.PARTIAL.name());
    }

    public void updateTotalAmountPaid(String paymentId, BigDecimal amountPaid) {
        this.updateAmounPaidAndPaymentStatus(paymentId, amountPaid, PaymentStatus.TOTAL.name());
    }

    public void updateExcedeedAmountPaid(String paymentId, BigDecimal amountPaid) {
        this.updateAmounPaidAndPaymentStatus(paymentId, amountPaid, PaymentStatus.EXCEDEED.name());
    }

    public void updateAmounPaidAndPaymentStatus(String paymentId, BigDecimal amountPaid, String paymentStatus) {
        paymentRepository.updateAmountPaid(paymentId, amountPaid, paymentStatus);
    }
}
