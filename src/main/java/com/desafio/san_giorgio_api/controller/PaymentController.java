package com.desafio.san_giorgio_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.san_giorgio_api.controller.dto.PaymentRequest;
import com.desafio.san_giorgio_api.controller.dto.PaymentResponse;
import com.desafio.san_giorgio_api.controller.presenter.PaymentPresenter;
import com.desafio.san_giorgio_api.domain.usecase.ConfirmPaymentUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    final ConfirmPaymentUseCase confirmPayment;

    @PutMapping(path = "/api/payment")
    public ResponseEntity<PaymentResponse> setPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(PaymentPresenter.toDomain(confirmPayment.confirm(PaymentPresenter.toDomain(request))));
    }
}
