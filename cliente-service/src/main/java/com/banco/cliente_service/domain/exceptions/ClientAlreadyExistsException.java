package com.banco.cliente_service.domain.exceptions;

public class ClientAlreadyExistsException extends RuntimeException {

    public ClientAlreadyExistsException(String clientId) {
        super("Client already exists with id: " + clientId);
    }
}