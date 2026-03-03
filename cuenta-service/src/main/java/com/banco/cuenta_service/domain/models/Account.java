package com.banco.cuenta_service.domain.models;

import com.banco.cuenta_service.domain.exceptions.InactiveAccountException;
import com.banco.cuenta_service.domain.exceptions.InsufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal initialBalance;
    private BigDecimal availableBalance;
    private Boolean status;
    private String clientId;

    public BigDecimal applyMovement(MovementType type, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (!Boolean.TRUE.equals(this.status)) {
            throw new InactiveAccountException(this.accountNumber);
        }

        BigDecimal newBalance = switch (type) {
            case DEPOSIT -> availableBalance.add(amount);
            case WITHDRAWAL -> {
                BigDecimal result = availableBalance.subtract(amount);
                if (result.compareTo(BigDecimal.ZERO) < 0) {
                    throw new InsufficientBalanceException(availableBalance);
                }
                yield result;
            }
        };

        this.availableBalance = newBalance;
        return newBalance;
    }
}

