package com.banco.cuenta_service.infrastructure.config;

import com.banco.cuenta_service.domain.ports.in.AccountUseCase;
import com.banco.cuenta_service.domain.ports.in.MovementUseCase;
import com.banco.cuenta_service.domain.ports.out.AccountRepositoryPort;
import com.banco.cuenta_service.domain.ports.out.ClientValidationPort;
import com.banco.cuenta_service.domain.ports.out.MovementRepositoryPort;
import com.banco.cuenta_service.domain.usecase.AccountUseCaseImpl;
import com.banco.cuenta_service.domain.usecase.MovementUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public AccountUseCase accountUseCase(AccountRepositoryPort accountRepositoryPort,
                                         ClientValidationPort clientValidationPort) {
        return new AccountUseCaseImpl(accountRepositoryPort, clientValidationPort);
    }

    @Bean
    public MovementUseCase movementUseCase(MovementRepositoryPort movementRepositoryPort,
                                           AccountRepositoryPort accountRepositoryPort) {
        return new MovementUseCaseImpl(movementRepositoryPort, accountRepositoryPort);
    }
}