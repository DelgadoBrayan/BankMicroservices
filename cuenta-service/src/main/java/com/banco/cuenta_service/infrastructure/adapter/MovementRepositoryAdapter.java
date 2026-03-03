package com.banco.cuenta_service.infrastructure.adapter;

import com.banco.cuenta_service.domain.models.Movement;
import com.banco.cuenta_service.domain.ports.out.MovementRepositoryPort;
import com.banco.cuenta_service.infrastructure.mappers.MovementInfrastructureMapper;
import com.banco.cuenta_service.infrastructure.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MovementRepositoryAdapter implements MovementRepositoryPort {

    private final MovementRepository movementRepository;
    private final MovementInfrastructureMapper mapper;

    @Override
    public Mono<Movement> save(Movement movement) {
        var entity = mapper.toEntity(movement);
        entity.setCreatedAt(LocalDateTime.now());
        return movementRepository.save(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<List<Movement>> findAll(int page, int size) {
        long offset = (long) page * size;
        return movementRepository.findAllPaged(size,offset)
                .map(mapper::toDomain)
                .collectList();
    }

    @Override
    public Mono<Long> count() {
        return movementRepository.countAll();
    }

    @Override
    public Flux<Movement> findByAccountId(Long accountId) {
        return movementRepository.findByAccountId(accountId)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Movement> findByAccountIdAndDateBetween(Long accountId, LocalDateTime start, LocalDateTime end) {
        return movementRepository.findByAccountIdAndDateBetween(accountId, start, end)
                .map(mapper::toDomain);
    }
}