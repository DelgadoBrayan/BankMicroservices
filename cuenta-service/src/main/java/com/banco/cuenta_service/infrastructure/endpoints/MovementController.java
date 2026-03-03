package com.banco.cuenta_service.infrastructure.endpoints;

import com.banco.cuenta_service.application.dtos.MovementRequest;
import com.banco.cuenta_service.application.dtos.MovementResponse;
import com.banco.cuenta_service.application.dtos.PageResponse;
import com.banco.cuenta_service.application.services.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    @PostMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovementResponse> registerMovement(@PathVariable String accountNumber,
                                                   @Valid @RequestBody MovementRequest request) {
        return movementService.registerMovement(accountNumber, request);
    }

    @GetMapping
    public Mono<PageResponse<MovementResponse>> getAllMovements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return movementService.getAllMovements(page, size);
    }
}