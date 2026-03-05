package com.banco.cuenta_service.integration;

import com.banco.cuenta_service.application.dtos.AccountRequest;
import com.banco.cuenta_service.application.dtos.AccountResponse;
import com.banco.cuenta_service.application.dtos.MovementRequest;
import com.banco.cuenta_service.application.dtos.MovementResponse;
import com.banco.cuenta_service.application.dtos.PageResponse;
import com.banco.cuenta_service.domain.models.AccountType;
import com.banco.cuenta_service.domain.models.MovementType;
import com.banco.cuenta_service.infrastructure.repository.AccountRepository;
import com.banco.cuenta_service.infrastructure.repository.MovementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class AccountMovementIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementRepository movementRepository;

    private static MockWebServer mockWebServer;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void startMockServer() throws IOException {
        objectMapper = new ObjectMapper();
        mockWebServer = new MockWebServer();
        mockWebServer.start(8089);
    }

    @AfterAll
    static void stopMockServer() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void cleanUp() {
        movementRepository.deleteAll().block();
        accountRepository.deleteAll().block();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void enqueueClientExists() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("""
                        {
                          "id": 1,
                          "clientId": "jose01",
                          "status": true,
                          "person": {
                            "id": 1,
                            "name": "Jose Lema",
                            "identification": "0912345678",
                            "address": "Otavalo sn y principal",
                            "phone": "098254785"
                          }
                        }
                        """));
    }

    private void enqueueClientNotFound() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .addHeader("Content-Type", "application/json")
                .setBody("""
                        {
                          "status": 404,
                          "error": "Client Not Found",
                          "message": "Client not found with id: noexiste"
                        }
                        """));
    }

    private void createTestAccount(String accountNumber, String initialBalance, String clientId) {
        enqueueClientExists();

        AccountRequest request = AccountRequest.builder()
                .accountNumber(accountNumber)
                .accountType(AccountType.SAVINGS)
                .initialBalance(new BigDecimal(initialBalance))
                .status(true)
                .clientId(clientId)
                .build();

        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();
    }

    // ── Create Account ────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should create account successfully when client exists")
    void shouldCreateAccountSuccessfully() {
        enqueueClientExists();

        AccountRequest request = AccountRequest.builder()
                .accountNumber("478758")
                .accountType(AccountType.SAVINGS)
                .initialBalance(new BigDecimal("2000.00"))
                .status(true)
                .clientId("jose01")
                .build();

        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponse.class)
                .value(response -> {
                    assertThat(response.getAccountNumber()).isEqualTo("478758");
                    assertThat(response.getAvailableBalance()).isEqualByComparingTo("2000.00");
                    assertThat(response.getClientId()).isEqualTo("jose01");
                });
    }

    @Test
    @DisplayName("Should return conflict when account already exists")
    void shouldReturnConflictWhenAccountAlreadyExists() {
        enqueueClientExists();
        enqueueClientExists();

        AccountRequest request = AccountRequest.builder()
                .accountNumber("478758")
                .accountType(AccountType.CHECKING)
                .initialBalance(new BigDecimal("2000.00"))
                .status(true)
                .clientId("jose01")
                .build();

        webTestClient.post().uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();

        webTestClient.post().uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    // ── Register Movement ─────────────────────────────────────────────────────

    @Test
    @DisplayName("Should register credit movement and update balance")
    void shouldRegisterCreditMovementAndUpdateBalance() {
        createTestAccount("478758", "500.00", "jose01");

        MovementRequest credit = MovementRequest.builder()
                .movementType(MovementType.DEPOSIT)
                .amount(new BigDecimal("300.00"))
                .build();

        webTestClient.post()
                .uri("/movements/478758")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(credit)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(MovementResponse.class)
                .value(response -> {
                    assertThat(response.getMovementType()).isEqualTo(MovementType.DEPOSIT);
                    assertThat(response.getAmount()).isEqualByComparingTo("300.00");
                    assertThat(response.getBalance()).isEqualByComparingTo("800.00");
                });
    }

    @Test
    @DisplayName("Should return 400 when insufficient balance")
    void shouldReturn400WhenInsufficientBalance() {
        createTestAccount("478758", "100.00", "jose01");

        MovementRequest bigDebit = MovementRequest.builder()
                .movementType(MovementType.WITHDRAWAL)
                .amount(new BigDecimal("500.00"))
                .build();

        webTestClient.post()
                .uri("/movements/478758")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bigDebit)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Insufficient Balance");
    }

    // ── Full Flow ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should complete full flow: create account, multiple movements, validate final balance")
    void shouldCompleteFullFlow() {

        createTestAccount("478758", "2000.00", "jose01");

        webTestClient.post().uri("/movements/478758")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(MovementRequest.builder()
                        .movementType(MovementType.WITHDRAWAL)
                        .amount(new BigDecimal("575.00")).build())
                .exchange()
                .expectStatus().isCreated();

        webTestClient.post().uri("/movements/478758")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(MovementRequest.builder()
                        .movementType(MovementType.DEPOSIT)
                        .amount(new BigDecimal("600.00")).build())
                .exchange()
                .expectStatus().isCreated();

        webTestClient.post().uri("/movements/478758")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(MovementRequest.builder()
                        .movementType(MovementType.WITHDRAWAL)
                        .amount(new BigDecimal("150.00")).build())
                .exchange()
                .expectStatus().isCreated();

        webTestClient.get()
                .uri("/accounts/478758")
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountResponse.class)
                .value(response ->
                        assertThat(response.getAvailableBalance())
                                .isEqualByComparingTo("1875.00"));

        webTestClient.get()
                .uri("/movements?page=0&size=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<PageResponse<MovementResponse>>() {})
                .value(page -> {
                    assertThat(page.getTotalElements()).isEqualTo(3);
                    assertThat(page.getContent()).hasSize(3);
                });
    }

    // ── Pagination ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should return paginated accounts")
    void shouldReturnPaginatedAccounts() {
        createTestAccount("478758", "2000.00", "jose01");
        enqueueClientExists();
        createTestAccount("225487", "500.00", "jose01");

        webTestClient.get()
                .uri("/accounts?page=0&size=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<PageResponse<AccountResponse>>() {})
                .value(page -> {
                    assertThat(page.getTotalElements()).isEqualTo(2);
                    assertThat(page.getContent()).hasSize(2);
                    assertThat(page.getPage()).isEqualTo(0);
                    assertThat(page.getSize()).isEqualTo(10);
                    assertThat(page.isFirst()).isTrue();
                    assertThat(page.isLast()).isTrue();
                });
    }

    @Test
    @DisplayName("Should return correct page size when paginating")
    void shouldReturnCorrectPageSizeWhenPaginating() {
        createTestAccount("478758", "2000.00", "jose01");
        enqueueClientExists();
        createTestAccount("225487", "500.00", "jose01");
        enqueueClientExists();
        createTestAccount("496825", "300.00", "jose01");

        webTestClient.get()
                .uri("/accounts?page=0&size=2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<PageResponse<AccountResponse>>() {})
                .value(page -> {
                    assertThat(page.getContent()).hasSize(2);
                    assertThat(page.getTotalElements()).isEqualTo(3);
                    assertThat(page.getTotalPages()).isEqualTo(2);
                    assertThat(page.isFirst()).isTrue();
                    assertThat(page.isLast()).isFalse();
                });

        webTestClient.get()
                .uri("/accounts?page=1&size=2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<PageResponse<AccountResponse>>() {})
                .value(page -> {
                    assertThat(page.getContent()).hasSize(1);
                    assertThat(page.isFirst()).isFalse();
                    assertThat(page.isLast()).isTrue();
                });
    }

    // ── Validation ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should return 400 when account number is missing")
    void shouldReturn400WhenAccountNumberIsMissing() {
        AccountRequest invalidRequest = AccountRequest.builder()
                .accountType(AccountType.CHECKING)
                .initialBalance(new BigDecimal("1000.00"))
                .status(true)
                .clientId("jose01")
                .build();

        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Validation Error");
    }
}