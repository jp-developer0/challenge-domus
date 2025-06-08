package domus.challenge.controller;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import domus.challenge.service.DirectorService;
import reactor.core.publisher.Mono;

@WebFluxTest(DirectorController.class)
class DirectorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DirectorService directorService;

    @Test
    void shouldReturnDirectorsAboveThreshold() {
        // Given
        int threshold = 2;
        List<String> directors = List.of("Martin Scorsese", "Woody Allen");
        when(directorService.getDirectorsAboveThreshold(threshold)).thenReturn(Mono.just(directors));

        // When/Then
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/directors").queryParam("threshold", "2").build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.directors").isArray()
                .jsonPath("$.directors[0]").isEqualTo("Martin Scorsese")
                .jsonPath("$.directors[1]").isEqualTo("Woody Allen");
    }

    @Test
    void shouldReturnBadRequestForNonNumericThreshold() {
        // When/Then
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/directors").queryParam("threshold", "abc").build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void shouldReturnEmptyArrayForNegativeThreshold() {
        // Given
        int threshold = -1;
        when(directorService.getDirectorsAboveThreshold(threshold)).thenReturn(Mono.just(List.of()));

        // When/Then
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/directors").queryParam("threshold", "-1").build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.directors").isArray()
                .jsonPath("$.directors").isEmpty();
    }
} 