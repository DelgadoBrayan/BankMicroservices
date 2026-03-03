package com.banco.cliente_service.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String gender;

    @Positive(message = "Age must be positive")
    private Integer age;

    @NotBlank(message = "Identification is required")
    private String identification;

    private String address;

    private String phone;
}