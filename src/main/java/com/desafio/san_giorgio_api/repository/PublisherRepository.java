package com.desafio.san_giorgio_api.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.desafio.san_giorgio_api.domain.model.PaymentMessage;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherRepository {
    @Value("${aws.queues.partial.name}")
    private String partialQueueName;

    @Value("${aws.queues.total.name}")
    private String totalQueueName;

    @Value("${aws.queues.excedeed.name}")
    private String excedeedQueueName;

    private final SqsTemplate sqsTemplate;

    public void sendMessageToPartialQueue(PaymentMessage content) {
        this.sendMessage(partialQueueName, content);
    }

    public void sendMessageToTotalQueue(PaymentMessage content) {
        this.sendMessage(totalQueueName, content);
    }

    public void sendMessageToExcedeedQueue(PaymentMessage content) {
        this.sendMessage(excedeedQueueName, content);
    }

    private void sendMessage(String queueName, PaymentMessage content) {
        sqsTemplate.send(to -> to.queue(queueName)
            .payload(content));
    }
}
