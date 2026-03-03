package com.banco.cliente_service.domain.ports.in;

import com.banco.cliente_service.domain.models.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClientUseCase {

    Mono<Client> createClient(Client client);

    Mono<List<Client>> getAllClients(int page, int size);

    Mono<Long> countClients();

    Mono<Client> getClientById(String clientId);

    Mono<Client> updateClient(String clientId, Client client);

    Mono<Void> deleteClient(String clientId);
}