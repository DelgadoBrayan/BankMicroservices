package com.banco.cuenta_service.domain.usecase;

import com.banco.cuenta_service.domain.exceptions.AccountNotFoundException;
import com.banco.cuenta_service.domain.models.Movement;
import com.banco.cuenta_service.domain.models.MovementType;
import com.banco.cuenta_service.domain.ports.in.MovementUseCase;
import com.banco.cuenta_service.domain.ports.out.AccountRepositoryPort;
import com.banco.cuenta_service.domain.ports.out.MovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class MovementUseCaseImpl implements MovementUseCase {

    private final MovementRepositoryPort movementRepositoryPort;
    private final AccountRepositoryPort accountRepositoryPort;

    @Override
    public Mono<Movement> registerMovement(String accountNumber, MovementType type, Movement movement) {
        return accountRepositoryPort.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new AccountNotFoundException(accountNumber)))
                .flatMap(account -> {

                    BigDecimal newBalance = account.applyMovement(type, movement.getAmount());

                    movement.setAccountId(account.getId());
                    movement.setBalance(newBalance);
                    movement.setDate(LocalDateTime.now());
                    movement.setMovementType(type);

                    return accountRepositoryPort.update(account)
                            .flatMap(updatedAccount -> movementRepositoryPort.save(movement));
                });
    }

    @Override
    public Mono<List<Movement>> getAllMovements(int page, int size) {
        return movementRepositoryPort.findAll(page, size);
    }

    @Override
    public Mono<Long> countMovements() {
        return movementRepositoryPort.count();
    }

    @Override
    public Flux<Movement> getMovementsByAccountId(Long accountId) {
        return movementRepositoryPort.findByAccountId(accountId);
    }
}
