package com.banco.cuenta_service.domain.ports.in;

import com.banco.cuenta_service.domain.models.Account;
import com.banco.cuenta_service.domain.models.Movement;
import com.banco.cuenta_service.domain.models.MovementType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MovementUseCase {

    Mono<Movement> registerMovement(String accountNumber, MovementType type, Movement movement);

    Mono<List<Movement>> getAllMovements(int page, int size);

    Mono<Long> countMovements();

    Flux<Movement> getMovementsByAccountId(Long accountId);
}