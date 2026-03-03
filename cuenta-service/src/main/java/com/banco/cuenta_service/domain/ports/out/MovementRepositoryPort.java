package com.banco.cuenta_service.domain.ports.out;

import com.banco.cuenta_service.domain.models.Account;
import com.banco.cuenta_service.domain.models.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MovementRepositoryPort {

    Mono<Movement> save(Movement movement);

    Mono<List<Movement>> findAll(int page, int size);

    Flux<Movement> findByAccountId(Long accountId);

    Mono<Long> count();

    Flux<Movement> findByAccountIdAndDateBetween(Long accountId, java.time.LocalDateTime start, java.time.LocalDateTime end);
}