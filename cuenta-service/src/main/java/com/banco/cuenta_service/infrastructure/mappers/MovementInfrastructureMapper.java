package com.banco.cuenta_service.infrastructure.mappers;

import com.banco.cuenta_service.domain.models.Movement;
import com.banco.cuenta_service.domain.models.MovementType;
import com.banco.cuenta_service.infrastructure.entities.MovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MovementInfrastructureMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "movementType", source = "movementType", qualifiedByName = "enumToString")
    MovementEntity toEntity(Movement movement);

    @Mapping(target = "movementType", source = "movementType", qualifiedByName = "stringToEnum")
    Movement toDomain(MovementEntity entity);

    @Named("enumToString")
    default String enumToString(MovementType type) {
        return type != null ? type.name() : null;
    }

    @Named("stringToEnum")
    default MovementType stringToEnum(String type) {
        return type != null ? MovementType.valueOf(type) : null;
    }
}