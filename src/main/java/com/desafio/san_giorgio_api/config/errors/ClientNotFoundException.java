package com.desafio.san_giorgio_api.config.errors;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String clientId) {
        super(String.format("Client %s not found.", clientId));
    }
}
