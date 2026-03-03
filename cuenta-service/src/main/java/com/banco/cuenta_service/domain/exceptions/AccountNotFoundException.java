package com.banco.cuenta_service.domain.exceptions;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountNumber) {
        super("Account not found with number: " + accountNumber);
    }
}