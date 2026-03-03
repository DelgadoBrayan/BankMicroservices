package com.banco.cuenta_service.domain.exceptions;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(BigDecimal currentBalance) {
        super("Insufficient balance. Current balance: " + currentBalance);
    }
}