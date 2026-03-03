package com.banco.cliente_service.infrastructure.mappers;

import com.banco.cliente_service.domain.models.Client;
import com.banco.cliente_service.domain.models.Person;
import com.banco.cliente_service.infrastructure.entities.ClientEntity;
import com.banco.cliente_service.infrastructure.entities.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientInfrastructureMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PersonEntity toEntity(Person person);

    @Mapping(target = "personId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ClientEntity toEntity(Client client);

    Person toDomain(PersonEntity entity);

    @Mapping(target = "person", ignore = true)
    Client toDomain(ClientEntity entity);
}