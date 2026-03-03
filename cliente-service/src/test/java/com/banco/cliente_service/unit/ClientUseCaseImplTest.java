package com.banco.cliente_service.unit;

import com.banco.cliente_service.domain.exceptions.ClientAlreadyExistsException;
import com.banco.cliente_service.domain.exceptions.ClientNotFoundException;
import com.banco.cliente_service.domain.models.Client;
import com.banco.cliente_service.domain.models.Person;
import com.banco.cliente_service.domain.ports.out.ClientRepositoryPort;
import com.banco.cliente_service.domain.usecase.ClientUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientUseCaseImplTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @InjectMocks
    private ClientUseCaseImpl clientUseCase;

    private Client client;
    private Person person;

    @BeforeEach
    void setUp() {
        person = Person.builder()
                .id(1L)
                .name("Jose Lema")
                .gender("Male")
                .age(30)
                .identification("0912345678")
                .address("Otavalo sn y principal")
                .phone("098254785")
                .build();

        client = Client.builder()
                .id(1L)
                .clientId("jose01")
                .password("1234")
                .status(true)
                .person(person)
                .build();
    }

    // ── createClient ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should create client successfully when clientId does not exist")
    void shouldCreateClientSuccessfully() {
        when(clientRepositoryPort.existsByClientId(anyString())).thenReturn(Mono.just(false));
        when(clientRepositoryPort.save(any(Client.class))).thenReturn(Mono.just(client));

        StepVerifier.create(clientUseCase.createClient(client))
                .expectNextMatches(result ->
                        result.getClientId().equals("jose01") &&
                        result.getStatus().equals(true) &&
                        result.getPerson().getName().equals("Jose Lema"))
                .verifyComplete();

        verify(clientRepositoryPort, times(1)).existsByClientId("jose01");
        verify(clientRepositoryPort, times(1)).save(any(Client.class));
    }

    @Test
    @DisplayName("Should throw ClientAlreadyExistsException when clientId already exists")
    void shouldThrowExceptionWhenClientAlreadyExists() {
        when(clientRepositoryPort.existsByClientId(anyString())).thenReturn(Mono.just(true));

        StepVerifier.create(clientUseCase.createClient(client))
                .expectError(ClientAlreadyExistsException.class)
                .verify();

        verify(clientRepositoryPort, times(1)).existsByClientId("jose01");
        verify(clientRepositoryPort, never()).save(any(Client.class));
    }

// ── getAllClients ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should return all clients successfully with pagination")
    void shouldReturnAllClientsSuccessfully() {
        Client client2 = Client.builder()
                .id(2L)
                .clientId("maria01")
                .password("5678")
                .status(true)
                .person(Person.builder().name("Maria Lopez").build())
                .build();

        when(clientRepositoryPort.findAll(0, 10))
                .thenReturn(Mono.just(List.of(client, client2)));

        StepVerifier.create(clientUseCase.getAllClients(0, 10))
                .expectNextMatches(result ->
                        result.size() == 2 &&
                                result.get(0).getClientId().equals("jose01") &&
                                result.get(1).getClientId().equals("maria01"))
                .verifyComplete();

        verify(clientRepositoryPort, times(1)).findAll(0, 10);
    }

    @Test
    @DisplayName("Should return empty list when no clients exist")
    void shouldReturnEmptyListWhenNoClientsExist() {
        when(clientRepositoryPort.findAll(0, 10))
                .thenReturn(Mono.just(List.of()));

        StepVerifier.create(clientUseCase.getAllClients(0, 10))
                .expectNextMatches(List::isEmpty)
                .verifyComplete();

        verify(clientRepositoryPort, times(1)).findAll(0, 10);
    }

    @Test
    @DisplayName("Should return correct page when requesting second page")
    void shouldReturnCorrectPageWhenRequestingSecondPage() {
        Client client3 = Client.builder()
                .id(3L)
                .clientId("carlos01")
                .password("9999")
                .status(true)
                .person(Person.builder().name("Carlos Perez").build())
                .build();

        when(clientRepositoryPort.findAll(1, 2))
                .thenReturn(Mono.just(List.of(client3)));

        StepVerifier.create(clientUseCase.getAllClients(1, 2))
                .expectNextMatches(result ->
                        result.size() == 1 &&
                                result.get(0).getClientId().equals("carlos01"))
                .verifyComplete();

        verify(clientRepositoryPort, times(1)).findAll(1, 2);
    }

    // ── getClientById ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should return client when found by clientId")
    void shouldReturnClientWhenFoundById() {
        when(clientRepositoryPort.findByClientId(anyString())).thenReturn(Mono.just(client));

        StepVerifier.create(clientUseCase.getClientById("jose01"))
                .expectNextMatches(result -> result.getClientId().equals("jose01"))
                .verifyComplete();

        verify(clientRepositoryPort, times(1)).findByClientId("jose01");
    }

    @Test
    @DisplayName("Should throw ClientNotFoundException when client does not exist")
    void shouldThrowClientNotFoundExceptionWhenClientDoesNotExist() {
        when(clientRepositoryPort.findByClientId(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(clientUseCase.getClientById("unknown"))
                .expectError(ClientNotFoundException.class)
                .verify();

        verify(clientRepositoryPort, times(1)).findByClientId("unknown");
    }

    // ── updateClient ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should update client successfully")
    void shouldUpdateClientSuccessfully() {
        Client updatedData = Client.builder()
                .clientId("jose01")
                .password("newpass")
                .status(false)
                .person(Person.builder()
                        .name("Jose Updated")
                        .age(31)
                        .build())
                .build();

        Client updatedClient = Client.builder()
                .id(1L)
                .clientId("jose01")
                .password("newpass")
                .status(false)
                .person(Person.builder().name("Jose Updated").age(31).build())
                .build();

        when(clientRepositoryPort.findByClientId(anyString())).thenReturn(Mono.just(client));
        when(clientRepositoryPort.update(any(Client.class))).thenReturn(Mono.just(updatedClient));

        StepVerifier.create(clientUseCase.updateClient("jose01", updatedData))
                .expectNextMatches(result ->
                        result.getPassword().equals("newpass") &&
                        result.getStatus().equals(false))
                .verifyComplete();

        verify(clientRepositoryPort, times(1)).findByClientId("jose01");
        verify(clientRepositoryPort, times(1)).update(any(Client.class));
    }

    @Test
    @DisplayName("Should throw ClientNotFoundException when updating non-existing client")
    void shouldThrowExceptionWhenUpdatingNonExistingClient() {
        when(clientRepositoryPort.findByClientId(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(clientUseCase.updateClient("unknown", client))
                .expectError(ClientNotFoundException.class)
                .verify();

        verify(clientRepositoryPort, never()).update(any(Client.class));
    }

    // ── deleteClient ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should delete client successfully")
    void shouldDeleteClientSuccessfully() {
        when(clientRepositoryPort.findByClientId(anyString())).thenReturn(Mono.just(client));
        when(clientRepositoryPort.deleteByClientId(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(clientUseCase.deleteClient("jose01"))
                .verifyComplete();

        verify(clientRepositoryPort, times(1)).findByClientId("jose01");
        verify(clientRepositoryPort, times(1)).deleteByClientId("jose01");
    }

    @Test
    @DisplayName("Should throw ClientNotFoundException when deleting non-existing client")
    void shouldThrowExceptionWhenDeletingNonExistingClient() {
        when(clientRepositoryPort.findByClientId(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(clientUseCase.deleteClient("unknown"))
                .expectError(ClientNotFoundException.class)
                .verify();

        verify(clientRepositoryPort, never()).deleteByClientId(anyString());
    }
}