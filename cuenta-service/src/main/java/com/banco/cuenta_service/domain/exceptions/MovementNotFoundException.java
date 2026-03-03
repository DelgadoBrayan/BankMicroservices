package com.banco.cuenta_service.domain.exceptions;

public class MovementNotFoundException extends RuntimeException {

    public MovementNotFoundException(Long id) {
        super("Movement not found with id: " + id);
    }
}