package com.banco.cliente_service.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private Long id;
    private Person person;
    private String clientId;
    private String password;
    private Boolean status;
}