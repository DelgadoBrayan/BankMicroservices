package com.banco.cliente_service.application.services;

import com.banco.cliente_service.application.dtos.ClientRequest;
import com.banco.cliente_service.application.dtos.ClientResponse;
import com.banco.cliente_service.application.dtos.PageResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Mono<ClientResponse> createClient(ClientRequest request);

    Mono<PageResponse<ClientResponse>> getAllClients(int page, int size);

    Mono<ClientResponse> getClientById(String clientId);

    Mono<ClientResponse> updateClient(String clientId, ClientRequest request);

    Mono<Void> deleteClient(String clientId);
}