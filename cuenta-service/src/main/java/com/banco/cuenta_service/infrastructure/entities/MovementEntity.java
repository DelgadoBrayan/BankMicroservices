package com.banco.cuenta_service.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("movements")
public class MovementEntity {

    @Id
    private Long id;

    @Column("account_id")
    private Long accountId;

    @Column("date")
    private LocalDateTime date;

    @Column("movement_type")
    private String movementType;

    @Column("amount")
    private BigDecimal amount;

    @Column("balance")
    private BigDecimal balance;

    @Column("created_at")
    private LocalDateTime createdAt;
}