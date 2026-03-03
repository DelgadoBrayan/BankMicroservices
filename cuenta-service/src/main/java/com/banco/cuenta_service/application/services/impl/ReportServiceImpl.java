package com.banco.cuenta_service.application.services.impl;

import com.banco.cuenta_service.application.dtos.ReportAccountDetail;
import com.banco.cuenta_service.application.dtos.ReportMovementDetail;
import com.banco.cuenta_service.application.dtos.ReportResponse;
import com.banco.cuenta_service.application.services.ReportService;
import com.banco.cuenta_service.domain.ports.out.AccountRepositoryPort;
import com.banco.cuenta_service.domain.ports.out.MovementRepositoryPort;
import com.banco.cuenta_service.infrastructure.webclient.ClientWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final AccountRepositoryPort accountRepositoryPort;
    private final MovementRepositoryPort movementRepositoryPort;
    private final ClientWebClient clientWebClient;

    @Override
    public Mono<ReportResponse> generateReport(String clientId, LocalDate startDate, LocalDate endDate) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return clientWebClient.getClientById(clientId)
                .flatMap(clientDto ->
                        accountRepositoryPort.findByClientId(clientId)
                                .flatMap(account ->
                                        movementRepositoryPort.findByAccountIdAndDateBetween(
                                                        account.getId(), startDateTime, endDateTime)
                                                .map(movement -> ReportMovementDetail.builder()
                                                        .date(movement.getDate())
                                                        .movementType(movement.getMovementType())
                                                        .amount(movement.getAmount())
                                                        .balance(movement.getBalance())
                                                        .build())
                                                .collectList()
                                                .map(movements -> ReportAccountDetail.builder()
                                                        .accountNumber(account.getAccountNumber())
                                                        .accountType(account.getAccountType())
                                                        .initialBalance(account.getInitialBalance())
                                                        .availableBalance(account.getAvailableBalance())
                                                        .status(account.getStatus())
                                                        .movements(movements)
                                                        .build())
                                )
                                .collectList()
                                .map(accounts -> ReportResponse.builder()
                                        .clientId(clientDto.getClientId())
                                        .clientName(clientDto.getPerson().getName())
                                        .accounts(accounts)
                                        .build())
                );
    }
}