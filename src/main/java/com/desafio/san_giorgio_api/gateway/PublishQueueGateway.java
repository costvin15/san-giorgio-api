package com.desafio.san_giorgio_api.gateway;

import org.springframework.stereotype.Service;

import com.desafio.san_giorgio_api.domain.model.PaymentItemModel;
import com.desafio.san_giorgio_api.gateway.presenter.PublishQueuePresenter;
import com.desafio.san_giorgio_api.repository.PublisherRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublishQueueGateway {
    PublisherRepository publisher;

    public void sendPaymentToPartialQueue(PaymentItemModel payment) {
        this.publisher.sendMessageToPartialQueue(PublishQueuePresenter.toDomain(payment));
    }

    public void sendPaymentToTotalQueue(PaymentItemModel payment) {
        this.publisher.sendMessageToTotalQueue(PublishQueuePresenter.toDomain(payment));
    }

    public void sendPaymentToExcedeedQueue(PaymentItemModel payment) {
        this.publisher.sendMessageToExcedeedQueue(PublishQueuePresenter.toDomain(payment));
    }
}
