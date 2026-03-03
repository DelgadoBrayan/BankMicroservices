package com.banco.cliente_service.application.services.impl;

import com.banco.cliente_service.application.dtos.ClientRequest;
import com.banco.cliente_service.application.dtos.ClientResponse;
import com.banco.cliente_service.application.dtos.PageResponse;
import com.banco.cliente_service.application.mappers.ClientMapper;
import com.banco.cliente_service.application.services.ClientService;
import com.banco.cliente_service.domain.ports.in.ClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientUseCase clientUseCase;
    private final ClientMapper clientMapper;

    @Override
    public Mono<ClientResponse> createClient(ClientRequest request) {
        return clientUseCase.createClient(clientMapper.toDomain(request))
                .map(clientMapper::toResponse);
    }

    @Override
    public Mono<PageResponse<ClientResponse>> getAllClients(int page, int size) {
        return Mono.zip(
                clientUseCase.getAllClients(page, size),
                clientUseCase.countClients()
        ).map(tuple -> {
            List<ClientResponse> content = tuple.getT1().stream()
                    .map(clientMapper::toResponse)
                    .toList();
            long totalElements = tuple.getT2();
            int totalPages = (int) Math.ceil((double) totalElements / size);

            return PageResponse.<ClientResponse>builder()
                    .content(content)
                    .page(page)
                    .size(size)
                    .totalElements(totalElements)
                    .totalPages(totalPages)
                    .first(page == 0)
                    .last(page >= totalPages - 1)
                    .build();
        });
    }

    @Override
    public Mono<ClientResponse> getClientById(String clientId) {
        return clientUseCase.getClientById(clientId)
                .map(clientMapper::toResponse);
    }

    @Override
    public Mono<ClientResponse> updateClient(String clientId, ClientRequest request) {
        return clientUseCase.updateClient(clientId, clientMapper.toDomain(request))
                .map(clientMapper::toResponse);
    }

    @Override
    public Mono<Void> deleteClient(String clientId) {
        return clientUseCase.deleteClient(clientId);
    }
}