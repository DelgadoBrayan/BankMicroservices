package com.banco.cuenta_service.domain.ports.out;

import reactor.core.publisher.Mono;

public interface ClientValidationPort {

    Mono<Boolean> clientExists(String clientId);
}