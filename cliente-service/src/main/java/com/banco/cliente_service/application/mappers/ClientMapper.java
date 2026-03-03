package com.banco.cliente_service.application.mappers;

import com.banco.cliente_service.application.dtos.ClientRequest;
import com.banco.cliente_service.application.dtos.ClientResponse;
import com.banco.cliente_service.application.dtos.PersonRequest;
import com.banco.cliente_service.application.dtos.PersonResponse;
import com.banco.cliente_service.domain.models.Client;
import com.banco.cliente_service.domain.models.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", source = "person")
    Client toDomain(ClientRequest request);

    @Mapping(target = "id", ignore = true)
    Person toDomain(PersonRequest request);

    ClientResponse toResponse(Client client);

    PersonResponse toResponse(Person person);
}