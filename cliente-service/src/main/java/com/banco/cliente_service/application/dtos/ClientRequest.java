package com.banco.cliente_service.application.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    @NotBlank(message = "Client ID is required")
    private String clientId;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Status is required")
    private Boolean status;

    @Valid
    @NotNull(message = "Person data is required")
    private PersonRequest person;
}