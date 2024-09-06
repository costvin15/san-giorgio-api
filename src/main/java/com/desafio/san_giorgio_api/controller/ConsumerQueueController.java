package com.desafio.san_giorgio_api.controller;

import org.springframework.stereotype.Component;

import com.desafio.san_giorgio_api.domain.model.PaymentMessage;
import com.desafio.san_giorgio_api.domain.usecase.ProcessExcedeedPaymentUseCase;
import com.desafio.san_giorgio_api.domain.usecase.ProcessPartialPaymentUseCase;
import com.desafio.san_giorgio_api.domain.usecase.ProcessTotalPaymentUseCase;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ConsumerQueueController {
    ProcessPartialPaymentUseCase processPartialPayment;
    ProcessTotalPaymentUseCase processTotalPayment;
    ProcessExcedeedPaymentUseCase processExcedeedPayment;

    @SqsListener(value = "${aws.queues.partial.name}")
    public void listenPartialPaymentsQueue(PaymentMessage payment) {
        processPartialPayment.confirm(payment);
    }

    @SqsListener(value = "${aws.queues.total.name}")
    public void listenTotalPaymentsQueue(PaymentMessage payment) {
        processTotalPayment.confirm(payment);
    }

    @SqsListener(value = "${aws.queues.excedeed.name}")
    public void listenExcedeedPaymentsQueue(PaymentMessage payment) {
        processExcedeedPayment.confirm(payment);
    }
}
