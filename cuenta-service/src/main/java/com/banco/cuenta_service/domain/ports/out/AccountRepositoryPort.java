package com.banco.cuenta_service.domain.ports.out;

import com.banco.cuenta_service.domain.models.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountRepositoryPort {

    Mono<Account> save(Account account);

    Mono<List<Account>> findAll(int page, int size);

    Mono<Long> count();

    Flux<Account> findByClientId(String clientId);

    Mono<Account> findByAccountNumber(String accountNumber);

    Mono<Boolean> existsByAccountNumber(String accountNumber);

    Mono<Account> update(Account account);

    Mono<Void> deleteByAccountNumber(String accountNumber);
}