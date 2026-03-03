package com.banco.cuenta_service.application.mappers;

import com.banco.cuenta_service.application.dtos.MovementRequest;
import com.banco.cuenta_service.application.dtos.MovementResponse;
import com.banco.cuenta_service.domain.models.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovementMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "movementType", ignore = true)
    @Mapping(target = "balance", ignore = true)
    Movement toDomain(MovementRequest request);

    MovementResponse toResponse(Movement movement);
}