package com.banco.cliente_service.infrastructure.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientProjection {

    private Long id;

    @Column("client_id")
    private String clientId;

    @Column("password")
    private String password;

    @Column("status")
    private Boolean status;

    @Column("person_id")
    private Long personId;

    @Column("name")
    private String name;

    @Column("gender")
    private String gender;

    @Column("age")
    private Integer age;

    @Column("identification")
    private String identification;

    @Column("address")
    private String address;

    @Column("phone")
    private String phone;
}