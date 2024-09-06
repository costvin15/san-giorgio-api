package com.desafio.san_giorgio_api.domain.usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.desafio.san_giorgio_api.config.errors.ClientNotFoundException;
import com.desafio.san_giorgio_api.config.errors.PaymentNotFoundException;
import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.domain.model.PaymentModel;
import com.desafio.san_giorgio_api.gateway.ClientGateway;
import com.desafio.san_giorgio_api.gateway.PaymentGateway;
import com.desafio.san_giorgio_api.gateway.PublishQueueGateway;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmPaymentUseCase {
    ClientGateway clientGateway;
    PaymentGateway paymentGateway;
    PublishQueueGateway queueGateway;

    public PaymentModel confirm(PaymentModel paymentModel) {
        if (!clientGateway.checkIfClientExists(paymentModel.getClientId())) {
            throw new ClientNotFoundException(paymentModel.getClientId());
        }

        List<String> paymentsIdsNonExistents = new ArrayList<>();
        Map<String, PaymentItemModel> paymentsInformations = new HashMap<>();

        for (PaymentItemModel item : paymentModel.getPaymentItems()) {
            Optional<PaymentItemModel> paymentInformation = paymentGateway.findPayment(item.getPaymentId());
            if (!paymentInformation.isPresent()) {
                paymentsIdsNonExistents.add(item.getPaymentId());
            } else {
                paymentsInformations.put(paymentInformation.get().getPaymentId(), paymentInformation.get());
            }
        }

        if (paymentsIdsNonExistents.size() > 0) {
            throw new PaymentNotFoundException(paymentsIdsNonExistents);
        }

        for (PaymentItemModel paymentReceived : paymentModel.getPaymentItems()) {
            PaymentItemModel paymentInformation = paymentsInformations.get(paymentReceived.getPaymentId());
            if (paymentGateway.checkIfPaymentWasPartial(paymentReceived, paymentInformation)) {
                queueGateway.sendPaymentToPartialQueue(paymentReceived);
            }

            if (paymentGateway.checkIfPaymentWasTotal(paymentReceived, paymentInformation)) {
                queueGateway.sendPaymentToTotalQueue(paymentReceived);
            }

            if (paymentGateway.checkIfPaymentWasExcedeed(paymentReceived, paymentInformation)) {
                queueGateway.sendPaymentToExcedeedQueue(paymentReceived);
            }
        }

        return clientGateway.prepareClientWithNewInformation(paymentModel);
    }
}
