package com.banco.cuenta_service.domain.exceptions;

public class InactiveAccountException extends RuntimeException {

    public InactiveAccountException(String accountNumber) {
        super("Account %s is inactive and cannot process transactions."
                .formatted(accountNumber));
    }
}