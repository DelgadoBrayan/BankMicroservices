package com.banco.cuenta_service.domain.ports.in;

import com.banco.cuenta_service.domain.models.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountUseCase {
    Mono<Account> createAccount(Account account);

    Mono<List<Account>> getAllAccounts(int page, int size);

    Mono<Long> countAccounts();

    Mono<Account> getAccountByNumber(String accountNumber);

    Mono<Account> updateAccount(String accountNumber, Account account);

    Mono<Void> deleteAccount(String accountNumber);
}