package com.banco.cuenta_service.application.services;

import com.banco.cuenta_service.application.dtos.AccountRequest;
import com.banco.cuenta_service.application.dtos.AccountResponse;
import com.banco.cuenta_service.application.dtos.PageResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<AccountResponse> createAccount(AccountRequest request);

    Mono<PageResponse<AccountResponse>> getAllAccounts(int page, int size);

    Mono<AccountResponse> getAccountByNumber(String accountNumber);

    Mono<AccountResponse> updateAccount(String accountNumber, AccountRequest request);

    Mono<Void> deleteAccount(String accountNumber);
}