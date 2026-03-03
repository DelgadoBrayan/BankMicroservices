package com.banco.cuenta_service.domain.usecase;

import com.banco.cuenta_service.domain.exceptions.AccountAlreadyExistsException;
import com.banco.cuenta_service.domain.exceptions.AccountNotFoundException;
import com.banco.cuenta_service.domain.models.Account;
import com.banco.cuenta_service.domain.ports.in.AccountUseCase;
import com.banco.cuenta_service.domain.ports.out.AccountRepositoryPort;
import com.banco.cuenta_service.domain.ports.out.ClientValidationPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class AccountUseCaseImpl implements AccountUseCase {

    private final AccountRepositoryPort accountRepositoryPort;
    private final ClientValidationPort clientValidationPort;

    @Override
    public Mono<Account> createAccount(Account account) {
        return clientValidationPort.clientExists(account.getClientId())
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new RuntimeException("Client not found: " + account.getClientId()));
                    }
                    return accountRepositoryPort.existsByAccountNumber(account.getAccountNumber());
                })
                .flatMap(exists -> {
                    if ((Boolean) exists) {
                        return Mono.error(new AccountAlreadyExistsException(account.getAccountNumber()));
                    }
                    account.setAvailableBalance(account.getInitialBalance());
                    return accountRepositoryPort.save(account);
                });
    }

    @Override
    public Mono<List<Account>> getAllAccounts(int page, int size) {
        return accountRepositoryPort.findAll(page, size);
    }

    @Override
    public Mono<Long> countAccounts() {
        return accountRepositoryPort.count();
    }

    @Override
    public Mono<Account> getAccountByNumber(String accountNumber) {
        return accountRepositoryPort.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new AccountNotFoundException(accountNumber)));
    }

    @Override
    public Mono<Account> updateAccount(String accountNumber, Account account) {
        return accountRepositoryPort.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new AccountNotFoundException(accountNumber)))
                .flatMap(existing -> {
                    if (account.getClientId() != null && !account.getClientId().equals(existing.getClientId())) {
                        return clientValidationPort.clientExists(account.getClientId())
                                .flatMap(exists -> {
                                    if (!exists) {
                                        return Mono.error(new RuntimeException("Client not found: " + account.getClientId()));
                                    }
                                    return updateAndSave(existing, account);
                                });
                    }
                    return updateAndSave(existing, account);
                });
    }

    @Override
    public Mono<Void> deleteAccount(String accountNumber) {
        return accountRepositoryPort.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new AccountNotFoundException(accountNumber)))
                .flatMap(existing -> accountRepositoryPort.deleteByAccountNumber(accountNumber));
    }

    private Mono<Account> updateAndSave(Account existing, Account update) {
        existing.setAccountType(update.getAccountType() != null ? update.getAccountType() : existing.getAccountType());
        existing.setStatus(update.getStatus() != null ? update.getStatus() : existing.getStatus());
        existing.setClientId(update.getClientId() != null ? update.getClientId() : existing.getClientId());
        return accountRepositoryPort.update(existing);
    }
}