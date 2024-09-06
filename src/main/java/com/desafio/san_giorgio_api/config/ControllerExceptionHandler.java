package com.desafio.san_giorgio_api.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.desafio.san_giorgio_api.config.errors.ClientNotFoundException;
import com.desafio.san_giorgio_api.config.errors.ErrorMessage;
import com.desafio.san_giorgio_api.config.errors.PaymentNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
        ClientNotFoundException.class,
        PaymentNotFoundException.class
    })
    public ResponseEntity<Object> handleResourceNotFoundException(RuntimeException exception, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), errorMessage.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.toList());
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.valueOf(status.value()), errors);
        return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());
    }
}
