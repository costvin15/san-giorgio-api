package com.desafio.san_giorgio_api.config.errors;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    public HttpStatus httpStatus;
    public List<String> errors;

    public ErrorMessage(HttpStatus httpStatus, String error) {
        this.httpStatus = httpStatus;
        this.errors = Arrays.asList(error);
    }
}
