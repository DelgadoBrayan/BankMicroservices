package com.banco.cuenta_service.domain.exceptions;

public class InvalidAccountTypeException extends RuntimeException {

    public InvalidAccountTypeException(String type) {
        super("Account type does not exist: " + type + ". Valid types are: SAVINGS, CHECKING");
    }
}