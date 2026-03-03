package com.banco.cuenta_service.application.dtos;

import com.banco.cuenta_service.domain.models.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementResponse {

    private Long id;
    private Long accountId;
    private LocalDateTime date;
    private MovementType movementType;
    private BigDecimal amount;
    private BigDecimal balance;
}