package com.banco.cliente_service.infrastructure.repository;

import com.banco.cliente_service.infrastructure.entities.PersonEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, Long> {
}