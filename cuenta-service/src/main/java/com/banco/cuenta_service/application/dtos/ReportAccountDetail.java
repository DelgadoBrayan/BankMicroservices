package com.banco.cuenta_service.application.dtos;

import com.banco.cuenta_service.domain.models.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportAccountDetail {

    private String accountNumber;
    private AccountType accountType;
    private BigDecimal initialBalance;
    private BigDecimal availableBalance;
    private Boolean status;
    private List<ReportMovementDetail> movements;
}