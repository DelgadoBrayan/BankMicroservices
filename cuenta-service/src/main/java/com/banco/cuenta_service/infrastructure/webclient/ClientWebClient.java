package com.banco.cuenta_service.infrastructure.webclient;

import com.banco.cuenta_service.domain.ports.out.ClientValidationPort;
import com.banco.cuenta_service.infrastructure.webclient.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientWebClient implements ClientValidationPort {

    private final WebClient webClient;

    @Value("${services.cliente-service.base-url}")
    private String baseUrl;

    public Mono<ClientDto> getClientById(String clientId) {
        return webClient.get()
                .uri(baseUrl + "/clients/{clientId}", clientId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Client not found: " + clientId)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("Client service error")))
                .bodyToMono(ClientDto.class);
    }

    @Override
    public Mono<Boolean> clientExists(String clientId) {
        return webClient.get()
                .uri(baseUrl + "/clients/{clientId}", clientId)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .onErrorReturn(false);
    }
}