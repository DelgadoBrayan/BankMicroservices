package com.banco.cuenta_service.domain.exceptions;

public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException(String accountNumber) {
        super("Account already exists with number: " + accountNumber);
    }
}