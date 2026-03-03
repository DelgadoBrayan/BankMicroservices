package com.banco.cuenta_service.infrastructure.adapter;

import com.banco.cuenta_service.domain.models.Account;
import com.banco.cuenta_service.domain.ports.out.AccountRepositoryPort;
import com.banco.cuenta_service.infrastructure.mappers.AccountInfrastructureMapper;
import com.banco.cuenta_service.infrastructure.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final AccountRepository accountRepository;
    private final AccountInfrastructureMapper mapper;

    @Override
    public Mono<Account> save(Account account) {
        var entity = mapper.toEntity(account);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<List<Account>> findAll(int page, int size) {
        long offset = (long) page * size;
        return accountRepository.findAllPaged(size, offset)
                .map(mapper::toDomain)
                .collectList();
    }

    @Override
    public Mono<Long> count() {
        return accountRepository.countAll();
    }

    @Override
    public Flux<Account> findByClientId(String clientId) {
        return accountRepository.findByClientId(clientId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Account> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByAccountNumber(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }

    @Override
    public Mono<Account> update(Account account) {
        return accountRepository.findByAccountNumber(account.getAccountNumber())
                .flatMap(entity -> {
                    entity.setAccountType(account.getAccountType().name());
                    entity.setAvailableBalance(account.getAvailableBalance());
                    entity.setStatus(account.getStatus());
                    entity.setClientId(account.getClientId());
                    entity.setUpdatedAt(LocalDateTime.now());
                    return accountRepository.save(entity)
                            .map(mapper::toDomain);
                });
    }

    @Override
    public Mono<Void> deleteByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .flatMap(accountRepository::delete);
    }
}