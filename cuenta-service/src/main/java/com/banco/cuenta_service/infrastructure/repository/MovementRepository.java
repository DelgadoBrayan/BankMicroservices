package com.banco.cuenta_service.infrastructure.repository;

import com.banco.cuenta_service.infrastructure.entities.MovementEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<MovementEntity, Long> {

    Flux<MovementEntity> findByAccountId(Long accountId);

    @Query("SELECT * FROM movements ORDER BY id LIMIT :size OFFSET :offset")
    Flux<MovementEntity> findAllPaged(int size, long offset);

    @Query("SELECT COUNT(*) FROM movements")
    Mono<Long> countAll();

    @Query("SELECT * FROM movements WHERE account_id = :accountId AND date BETWEEN :start AND :end")
    Flux<MovementEntity> findByAccountIdAndDateBetween(Long accountId, LocalDateTime start, LocalDateTime end);
}