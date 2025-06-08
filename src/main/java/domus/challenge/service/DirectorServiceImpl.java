package domus.challenge.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import domus.challenge.model.Movie;
import domus.challenge.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class DirectorServiceImpl implements DirectorService {

    private final MovieRepository movieRepository;

    @Override
    public Mono<List<String>> getDirectorsAboveThreshold(int threshold) {
        log.info("Finding directors above threshold: {}", threshold);
        
        if (threshold < 0) {
            log.info("Negative threshold provided, returning empty list");
            return Mono.just(List.of());
        }

        return movieRepository.getMoviesByPage(1)
                .flatMap(firstPageResponse -> {
                    int totalPages = firstPageResponse.getTotal_pages();
                    log.info("First page fetched, total pages: {}", totalPages);
                    
                    // Process first page and fetch all remaining pages
                    return Flux.concat(
                            Flux.just(firstPageResponse),
                            Flux.range(2, totalPages - 1)
                                .flatMap(movieRepository::getMoviesByPage)
                        )
                        .flatMapIterable(response -> response.getData())
                        .doOnNext(movie -> {
                            if (movie.getDirector() == null || movie.getDirector().isEmpty()) {
                                log.warn("Movie without director: {}", movie.getTitle());
                            } else {
                                log.debug("Processing movie: {}, Director: {}", 
                                    movie.getTitle(), movie.getDirector());
                            }
                        })
                        // Ensure we only include movies with valid directors
                        .filter(movie -> movie.getDirector() != null && !movie.getDirector().trim().isEmpty())
                        .collectMultimap(Movie::getDirector)
                        .doOnNext(map -> {
                            log.info("Collected {} unique directors", map.size());
                            map.keySet().forEach(director -> 
                                log.info("Director: {} has {} movies", director, map.get(director).size()));
                        })
                        .map(directorMoviesMap -> filterAndSortDirectors(directorMoviesMap, threshold));
                });
    }
    
    private List<String> filterAndSortDirectors(Map<String, Collection<Movie>> directorMoviesMap, int threshold) {
        List<String> result = directorMoviesMap.entrySet().stream()
                .filter(entry -> entry.getValue().size() > threshold)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
        
        log.info("Filtered directors above threshold {}: found {} directors", threshold, result.size());
        result.forEach(director -> log.info("Director: {} with {} movies", 
                director, directorMoviesMap.get(director).size()));
        
        return result;
    }
} 