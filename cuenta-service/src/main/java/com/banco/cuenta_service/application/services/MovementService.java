package com.banco.cuenta_service.application.services;

import com.banco.cuenta_service.application.dtos.AccountResponse;
import com.banco.cuenta_service.application.dtos.MovementRequest;
import com.banco.cuenta_service.application.dtos.MovementResponse;
import com.banco.cuenta_service.application.dtos.PageResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {

    Mono<MovementResponse> registerMovement(String accountNumber, MovementRequest request);

    Mono<PageResponse<MovementResponse>> getAllMovements(int page, int size);
}