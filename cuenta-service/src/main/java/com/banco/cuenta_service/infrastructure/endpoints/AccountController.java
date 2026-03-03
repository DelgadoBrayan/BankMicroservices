package com.banco.cuenta_service.infrastructure.endpoints;

import com.banco.cuenta_service.application.dtos.AccountRequest;
import com.banco.cuenta_service.application.dtos.AccountResponse;
import com.banco.cuenta_service.application.dtos.PageResponse;
import com.banco.cuenta_service.application.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
        return accountService.createAccount(request);
    }

    @GetMapping
    public Mono<PageResponse<AccountResponse>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return accountService.getAllAccounts(page, size);
    }

    @GetMapping("/{accountNumber}")
    public Mono<AccountResponse> getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }

    @PutMapping("/{accountNumber}")
    public Mono<AccountResponse> updateAccount(@PathVariable String accountNumber,
                                               @Valid @RequestBody AccountRequest request) {
        return accountService.updateAccount(accountNumber, request);
    }

    @DeleteMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAccount(@PathVariable String accountNumber) {
        return accountService.deleteAccount(accountNumber);
    }
}