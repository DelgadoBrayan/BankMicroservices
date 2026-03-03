package com.banco.cuenta_service.application.services.impl;

import com.banco.cuenta_service.application.dtos.AccountRequest;
import com.banco.cuenta_service.application.dtos.AccountResponse;
import com.banco.cuenta_service.application.dtos.PageResponse;
import com.banco.cuenta_service.application.mappers.AccountMapper;
import com.banco.cuenta_service.application.services.AccountService;
import com.banco.cuenta_service.domain.ports.in.AccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountUseCase accountUseCase;
    private final AccountMapper accountMapper;

    @Override
    public Mono<AccountResponse> createAccount(AccountRequest request) {
        return accountUseCase.createAccount(accountMapper.toDomain(request))
                .map(accountMapper::toResponse);
    }

    @Override
    public Mono<PageResponse<AccountResponse>> getAllAccounts(int page, int size) {
        return Mono.zip(
                accountUseCase.getAllAccounts(page, size),
                accountUseCase.countAccounts()
        ).map(tuple -> {
            List<AccountResponse> content = tuple.getT1().stream()
                    .map(accountMapper::toResponse)
                    .toList();
            long totalElements = tuple.getT2();
            int totalPages = (int) Math.ceil((double) totalElements / size);

            return PageResponse.<AccountResponse>builder()
                    .content(content)
                    .page(page)
                    .size(size)
                    .totalElements(totalElements)
                    .totalPages(totalPages)
                    .first(page == 0)
                    .last(page >= totalPages - 1)
                    .build();
        });
    }

    @Override
    public Mono<AccountResponse> getAccountByNumber(String accountNumber) {
        return accountUseCase.getAccountByNumber(accountNumber)
                .map(accountMapper::toResponse);
    }

    @Override
    public Mono<AccountResponse> updateAccount(String accountNumber, AccountRequest request) {
        return accountUseCase.updateAccount(accountNumber, accountMapper.toDomain(request))
                .map(accountMapper::toResponse);
    }

    @Override
    public Mono<Void> deleteAccount(String accountNumber) {
        return accountUseCase.deleteAccount(accountNumber);
    }
}