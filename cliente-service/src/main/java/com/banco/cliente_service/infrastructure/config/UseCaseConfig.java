package com.banco.cliente_service.infrastructure.config;

import com.banco.cliente_service.domain.ports.in.ClientUseCase;
import com.banco.cliente_service.domain.ports.out.ClientRepositoryPort;
import com.banco.cliente_service.domain.usecase.ClientUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public ClientUseCase clientUseCase(ClientRepositoryPort clientRepositoryPort) {
        return new ClientUseCaseImpl(clientRepositoryPort);
    }
}