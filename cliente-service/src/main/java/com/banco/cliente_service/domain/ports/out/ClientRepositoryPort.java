package com.banco.cliente_service.domain.ports.out;

import com.banco.cliente_service.domain.models.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClientRepositoryPort {

    Mono<Client> save(Client client);

    Mono<List<Client>> findAll(int page, int size);

    Mono<Long> count();

    Mono<Client> findByClientId(String clientId);

    Mono<Boolean> existsByClientId(String clientId);

    Mono<Client> update(Client client);

    Mono<Void> deleteByClientId(String clientId);
}