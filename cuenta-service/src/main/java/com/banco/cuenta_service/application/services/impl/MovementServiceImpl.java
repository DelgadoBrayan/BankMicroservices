package com.banco.cuenta_service.application.services.impl;

import com.banco.cuenta_service.application.dtos.AccountResponse;
import com.banco.cuenta_service.application.dtos.MovementRequest;
import com.banco.cuenta_service.application.dtos.MovementResponse;
import com.banco.cuenta_service.application.dtos.PageResponse;
import com.banco.cuenta_service.application.mappers.MovementMapper;
import com.banco.cuenta_service.application.services.MovementService;
import com.banco.cuenta_service.domain.ports.in.MovementUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementUseCase movementUseCase;
    private final MovementMapper movementMapper;

    @Override
    public Mono<MovementResponse> registerMovement(String accountNumber, MovementRequest request) {
        return movementUseCase.registerMovement(
                        accountNumber,
                        request.getMovementType(),
                        movementMapper.toDomain(request))
                .map(movementMapper::toResponse);
    }

    @Override
    public Mono<PageResponse<MovementResponse>> getAllMovements(int page, int size) {
        return Mono.zip(
                movementUseCase.getAllMovements(page, size),
                movementUseCase.countMovements()
        ).map(tuple -> {
            List<MovementResponse> content = tuple.getT1().stream()
                    .map(movementMapper::toResponse)
                    .toList();
            long totalElements = tuple.getT2();
            int totalPages = (int) Math.ceil((double) totalElements / size);

            return PageResponse.<MovementResponse>builder()
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
}
