package com.desafio.san_giorgio_api.gateway;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.domain.model.PaymentModel;
import com.desafio.san_giorgio_api.gateway.presenter.ClientPresenter;
import com.desafio.san_giorgio_api.repository.ClientRepository;
import com.desafio.san_giorgio_api.repository.entities.PaymentStatus;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientGateway {
    PaymentGateway paymentGateway;
    ClientRepository clientRepository;

    public boolean checkIfClientExists(String clientId) {
        return this.clientRepository.checkIfClientExists(clientId);
    }

    public PaymentModel findClientInformation(String clientId) {
        return ClientPresenter.toDomain(this.clientRepository.getReferenceById(clientId));
    }

    public PaymentModel prepareClientWithNewInformation(PaymentModel clientReceived) {
        PaymentModel clientInformation = ClientPresenter.toDomain(this.clientRepository.getReferenceById(clientReceived.getClientId()));

        Map<String, PaymentItemModel> paymentsReceived = new HashMap<>();
        for (PaymentItemModel payment : clientReceived.getPaymentItems()) {
            paymentsReceived.put(payment.getPaymentId(), payment);
        }

        PaymentModel result = PaymentModel.builder()
            .clientId(clientReceived.getClientId())
            .paymentItems(new ArrayList<>())
            .build();

        for (PaymentItemModel paymentInformation : clientInformation.getPaymentItems()) {
            PaymentItemModel paymentReceived = paymentsReceived.get(paymentInformation.getPaymentId());
            BigDecimal amountPaid = paymentInformation.getAmountPaid();
            String paymentStatus = paymentInformation.getPaymentStatus();

            if (paymentReceived != null) {
                amountPaid = amountPaid.add(paymentReceived.getPaymentValue());

                if (paymentGateway.checkIfPaymentWasPartial(paymentReceived, paymentInformation)) {
                    paymentStatus = PaymentStatus.PARTIAL.name();
                }

                if (paymentGateway.checkIfPaymentWasTotal(paymentReceived, paymentInformation)) {
                    paymentStatus = PaymentStatus.TOTAL.name();
                }
    
                if (paymentGateway.checkIfPaymentWasExcedeed(paymentReceived, paymentInformation)) {
                    paymentStatus = PaymentStatus.EXCEDEED.name();
                }
            }

            PaymentItemModel item = PaymentItemModel.builder()
                .paymentId(paymentInformation.getPaymentId())
                .amountPaid(amountPaid)
                .paymentValue(paymentInformation.getPaymentValue())
                .paymentStatus(paymentStatus)
                .build();

            result.getPaymentItems().add(item);
        }

        return result;
    }
}
