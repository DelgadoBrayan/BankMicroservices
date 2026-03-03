package com.banco.cliente_service.infrastructure.endpoints;

import com.banco.cliente_service.application.dtos.ClientRequest;
import com.banco.cliente_service.application.dtos.ClientResponse;
import com.banco.cliente_service.application.dtos.PageResponse;
import com.banco.cliente_service.application.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClientResponse> createClient(@Valid @RequestBody ClientRequest request) {
        return clientService.createClient(request);
    }

    @GetMapping
    public Mono<PageResponse<ClientResponse>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return clientService.getAllClients(page, size);
    }

    @GetMapping("/{clientId}")
    public Mono<ClientResponse> getClientById(@PathVariable String clientId) {
        return clientService.getClientById(clientId);
    }

    @PutMapping("/{clientId}")
    public Mono<ClientResponse> updateClient(@PathVariable String clientId,
                                             @Valid @RequestBody ClientRequest request) {
        return clientService.updateClient(clientId, request);
    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteClient(@PathVariable String clientId) {
        return clientService.deleteClient(clientId);
    }
}