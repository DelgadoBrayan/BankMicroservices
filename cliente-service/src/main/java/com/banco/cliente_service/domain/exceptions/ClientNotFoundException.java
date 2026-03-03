package com.banco.cliente_service.domain.exceptions;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(String clientId) {
        super("Client not found with id: " + clientId);
    }
}