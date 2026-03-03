package com.banco.cuenta_service.infrastructure.repository;

import com.banco.cuenta_service.infrastructure.entities.AccountEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, Long> {

    Mono<AccountEntity> findByAccountNumber(String accountNumber);

    Mono<Boolean> existsByAccountNumber(String accountNumber);

    @Query("SELECT * FROM accounts ORDER BY id LIMIT :size OFFSET :offset")
    Flux<AccountEntity> findAllPaged(int size, long offset);

    @Query("SELECT * FROM accounts WHERE client_id = :clientId")
    Flux<AccountEntity> findByClientId(String clientId);

    @Query("SELECT COUNT(*) FROM accounts")
    Mono<Long> countAll();
}