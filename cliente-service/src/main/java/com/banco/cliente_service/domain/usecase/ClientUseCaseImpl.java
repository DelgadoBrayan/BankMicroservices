package com.banco.cliente_service.domain.usecase;

import com.banco.cliente_service.domain.exceptions.ClientAlreadyExistsException;
import com.banco.cliente_service.domain.exceptions.ClientNotFoundException;
import com.banco.cliente_service.domain.models.Client;
import com.banco.cliente_service.domain.ports.in.ClientUseCase;
import com.banco.cliente_service.domain.ports.out.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class ClientUseCaseImpl implements ClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public Mono<Client> createClient(Client client) {
        return clientRepositoryPort.existsByClientId(client.getClientId())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ClientAlreadyExistsException(client.getClientId()));
                    }
                    return clientRepositoryPort.save(client);
                });
    }

    @Override
    public Mono<List<Client>> getAllClients(int page, int size) {
        return clientRepositoryPort.findAll(page, size);
    }

    @Override
    public Mono<Long> countClients() {
        return clientRepositoryPort.count();
    }

    @Override
    public Mono<Client> getClientById(String clientId) {
        return clientRepositoryPort.findByClientId(clientId)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(clientId)));
    }

    @Override
    public Mono<Client> updateClient(String clientId, Client client) {
        return clientRepositoryPort.findByClientId(clientId)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(clientId)))
                .flatMap(existing -> {
                    existing.setClientId(client.getClientId() != null ? client.getClientId() : existing.getClientId());
                    existing.setPassword(client.getPassword() != null ? client.getPassword() : existing.getPassword());
                    existing.setStatus(client.getStatus() != null ? client.getStatus() : existing.getStatus());
                    if (client.getPerson() != null) {
                        existing.getPerson().setName(client.getPerson().getName() != null ? client.getPerson().getName() : existing.getPerson().getName());
                        existing.getPerson().setGender(client.getPerson().getGender() != null ? client.getPerson().getGender() : existing.getPerson().getGender());
                        existing.getPerson().setAge(client.getPerson().getAge() != null ? client.getPerson().getAge() : existing.getPerson().getAge());
                        existing.getPerson().setAddress(client.getPerson().getAddress() != null ? client.getPerson().getAddress() : existing.getPerson().getAddress());
                        existing.getPerson().setPhone(client.getPerson().getPhone() != null ? client.getPerson().getPhone() : existing.getPerson().getPhone());
                    }
                    return clientRepositoryPort.update(existing);
                });
    }

    @Override
    public Mono<Void> deleteClient(String clientId) {
        return clientRepositoryPort.findByClientId(clientId)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(clientId)))
                .flatMap(existing -> clientRepositoryPort.deleteByClientId(clientId));
    }
}
