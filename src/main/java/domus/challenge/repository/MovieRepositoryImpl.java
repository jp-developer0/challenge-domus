package domus.challenge.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import domus.challenge.model.MovieResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MovieRepositoryImpl implements MovieRepository {
    
    private final WebClient webClient;
    
    @Override
    public Mono<MovieResponse> getMoviesByPage(int page) {
        log.info("Fetching movies for page: {}", page);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("page", page).build())
                .retrieve()
                .bodyToMono(MovieResponse.class)
                .doOnNext(response -> {
                    log.info("Received response for page {}: {} movies", 
                        page, response.getData() != null ? response.getData().size() : 0);
                    
                    // Log a sample movie to verify fields
                    if (response.getData() != null && !response.getData().isEmpty()) {
                        log.info("Sample movie from page {}: Director='{}', Title='{}'", 
                            page, response.getData().get(0).getDirector(), 
                            response.getData().get(0).getTitle());
                    }
                })
                .doOnError(error -> log.error("Error fetching movies for page {}: {}", 
                    page, error.getMessage()));
    }
} 