package com.banco.cliente_service.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("clients")
public class ClientEntity {

    @Id
    private Long id;

    @Column("person_id")
    private Long personId;

    @Column("client_id")
    private String clientId;

    @Column("password")
    private String password;

    @Column("status")
    private Boolean status;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}