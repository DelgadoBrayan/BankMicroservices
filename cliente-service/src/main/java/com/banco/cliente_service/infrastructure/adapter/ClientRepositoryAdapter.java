package com.banco.cliente_service.infrastructure.adapter;

import com.banco.cliente_service.domain.models.Client;
import com.banco.cliente_service.domain.models.Person;
import com.banco.cliente_service.domain.ports.out.ClientRepositoryPort;
import com.banco.cliente_service.infrastructure.mappers.ClientInfrastructureMapper;
import com.banco.cliente_service.infrastructure.repository.ClientRepository;
import com.banco.cliente_service.infrastructure.repository.PersonRepository;
import com.banco.cliente_service.infrastructure.repository.projection.ClientProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;
    private final ClientInfrastructureMapper mapper;

    @Override
    public Mono<Client> save(Client client) {
        var personEntity = mapper.toEntity(client.getPerson());
        personEntity.setCreatedAt(LocalDateTime.now());
        personEntity.setUpdatedAt(LocalDateTime.now());

        return personRepository.save(personEntity)
                .flatMap(savedPerson -> {
                    var clientEntity = mapper.toEntity(client);
                    clientEntity.setPersonId(savedPerson.getId());
                    clientEntity.setCreatedAt(LocalDateTime.now());
                    clientEntity.setUpdatedAt(LocalDateTime.now());
                    return clientRepository.save(clientEntity)
                            .map(savedClient -> {
                                Client result = mapper.toDomain(savedClient);
                                result.setPerson(mapper.toDomain(savedPerson));
                                return result;
                            });
                });
    }

    @Override
    public Mono<List<Client>> findAll(int page, int size) {
        long offset = (long) page * size;
        return clientRepository.findAllWithPersonPaged(size, offset)
                .map(this::fromProjection)
                .collectList();
    }

    @Override
    public Mono<Long> count() {
        return clientRepository.countAll();
    }

    @Override
    public Mono<Client> findByClientId(String clientId) {
        return clientRepository.findByClientIdWithPerson(clientId)
                .map(this::fromProjection);
    }

    @Override
    public Mono<Boolean> existsByClientId(String clientId) {
        return clientRepository.existsByClientId(clientId);
    }

    @Override
    public Mono<Client> update(Client client) {
        return clientRepository.findByClientId(client.getClientId())
                .flatMap(clientEntity ->
                        personRepository.findById(clientEntity.getPersonId())
                                .flatMap(personEntity -> {
                                    personEntity.setName(client.getPerson().getName());
                                    personEntity.setGender(client.getPerson().getGender());
                                    personEntity.setAge(client.getPerson().getAge());
                                    personEntity.setAddress(client.getPerson().getAddress());
                                    personEntity.setPhone(client.getPerson().getPhone());
                                    personEntity.setUpdatedAt(LocalDateTime.now());

                                    return personRepository.save(personEntity)
                                            .flatMap(savedPerson -> {
                                                clientEntity.setPassword(client.getPassword());
                                                clientEntity.setStatus(client.getStatus());
                                                clientEntity.setUpdatedAt(LocalDateTime.now());
                                                return clientRepository.save(clientEntity)
                                                        .map(savedClient -> {
                                                            Client result = mapper.toDomain(savedClient);
                                                            result.setPerson(mapper.toDomain(savedPerson));
                                                            return result;
                                                        });
                                            });
                                })
                );
    }

    @Override
    public Mono<Void> deleteByClientId(String clientId) {
        return clientRepository.findByClientId(clientId)
                .flatMap(clientEntity ->
                        clientRepository.delete(clientEntity)
                                .then(personRepository.deleteById(clientEntity.getPersonId()))
                );
    }

    private Client fromProjection(ClientProjection p) {
        Person person = Person.builder()
                .id(p.getPersonId())
                .name(p.getName())
                .gender(p.getGender())
                .age(p.getAge())
                .identification(p.getIdentification())
                .address(p.getAddress())
                .phone(p.getPhone())
                .build();

        return Client.builder()
                .id(p.getId())
                .clientId(p.getClientId())
                .password(p.getPassword())
                .status(p.getStatus())
                .person(person)
                .build();
    }
}