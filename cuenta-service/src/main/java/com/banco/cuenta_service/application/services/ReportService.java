package com.banco.cuenta_service.application.services;

import com.banco.cuenta_service.application.dtos.ReportResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface ReportService {

    Mono<ReportResponse> generateReport(String clientId, LocalDate startDate, LocalDate endDate);
}