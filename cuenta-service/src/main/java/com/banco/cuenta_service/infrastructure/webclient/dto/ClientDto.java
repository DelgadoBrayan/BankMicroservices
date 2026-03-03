package com.banco.cuenta_service.infrastructure.webclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long id;
    private String clientId;
    private Boolean status;
    private PersonDto person;
}