package com.banco.cliente_service.infrastructure.repository;

import com.banco.cliente_service.infrastructure.entities.ClientEntity;
import com.banco.cliente_service.infrastructure.repository.projection.ClientProjection;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<ClientEntity, Long> {

    Mono<ClientEntity> findByClientId(String clientId);

    Mono<Boolean> existsByClientId(String clientId);

    @Query("""
            SELECT c.id, c.client_id, c.password, c.status,
                   p.id as person_id, p.name, p.gender, p.age,
                   p.identification, p.address, p.phone
            FROM clients c
            INNER JOIN persons p ON c.person_id = p.id
            """)
    Flux<ClientProjection> findAllWithPerson();

    @Query("""
            SELECT c.id, c.client_id, c.password, c.status,
                   p.id as person_id, p.name, p.gender, p.age,
                   p.identification, p.address, p.phone
            FROM clients c
            INNER JOIN persons p ON c.person_id = p.id
            WHERE c.client_id = :clientId
            """)
    Mono<ClientProjection> findByClientIdWithPerson(String clientId);

    @Query("""
            SELECT c.id, c.client_id, c.password, c.status,
                   p.id as person_id, p.name, p.gender, p.age,
                   p.identification, p.address, p.phone
            FROM clients c
            INNER JOIN persons p ON c.person_id = p.id
            ORDER BY c.id
            LIMIT :size OFFSET :offset
            """)
    Flux<ClientProjection> findAllWithPersonPaged(int size, long offset);

    @Query("SELECT COUNT(*) FROM clients")
    Mono<Long> countAll();
}