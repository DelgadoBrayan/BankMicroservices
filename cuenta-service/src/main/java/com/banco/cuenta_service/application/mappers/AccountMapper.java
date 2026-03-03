package com.banco.cuenta_service.application.mappers;

import com.banco.cuenta_service.application.dtos.AccountRequest;
import com.banco.cuenta_service.application.dtos.AccountResponse;
import com.banco.cuenta_service.domain.models.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "availableBalance", ignore = true)
    Account toDomain(AccountRequest request);

    AccountResponse toResponse(Account account);
}