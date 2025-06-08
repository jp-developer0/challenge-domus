package domus.challenge.repository;

import domus.challenge.model.MovieResponse;
import reactor.core.publisher.Mono;

public interface MovieRepository {
    Mono<MovieResponse> getMoviesByPage(int page);
} 