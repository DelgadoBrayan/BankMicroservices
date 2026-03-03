package com.banco.cuenta_service.infrastructure.mappers;

import com.banco.cuenta_service.domain.models.Account;
import com.banco.cuenta_service.domain.models.AccountType;
import com.banco.cuenta_service.infrastructure.entities.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AccountInfrastructureMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "accountType", source = "accountType", qualifiedByName = "enumToString")
    AccountEntity toEntity(Account account);

    @Mapping(target = "accountType", source = "accountType", qualifiedByName = "stringToEnum")
    Account toDomain(AccountEntity entity);

    @Named("enumToString")
    default String enumToString(AccountType type) {
        return type != null ? type.name() : null;
    }

    @Named("stringToEnum")
    default AccountType stringToEnum(String type) {
        return type != null ? AccountType.valueOf(type) : null;
    }
}