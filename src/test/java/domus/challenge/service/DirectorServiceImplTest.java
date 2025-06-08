package domus.challenge.service;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import domus.challenge.model.Movie;
import domus.challenge.model.MovieResponse;
import domus.challenge.repository.MovieRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class DirectorServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private DirectorServiceImpl directorService;

    private MovieResponse singlePageResponse;

    @BeforeEach
    void setUp() {
        // Create a sample movie response with directors
        Movie movie1 = new Movie("Movie 1", "2020", "PG", "01/01/2020", "120 min", "Action", "Martin Scorsese", "Writer 1", "Actor 1");
        Movie movie2 = new Movie("Movie 2", "2021", "PG-13", "01/01/2021", "130 min", "Drama", "Martin Scorsese", "Writer 2", "Actor 2");
        Movie movie3 = new Movie("Movie 3", "2022", "R", "01/01/2022", "140 min", "Comedy", "Woody Allen", "Writer 3", "Actor 3");
        Movie movie4 = new Movie("Movie 4", "2022", "R", "01/01/2022", "140 min", "Comedy", "Christopher Nolan", "Writer 4", "Actor 4");
        Movie movie5 = new Movie("Movie 5", "2022", "R", "01/01/2022", "140 min", "Comedy", "Woody Allen", "Writer 5", "Actor 5");
        Movie movie6 = new Movie("Movie 6", "2022", "R", "01/01/2022", "140 min", "Comedy", "Martin Scorsese", "Writer 6", "Actor 6");
        
        singlePageResponse = new MovieResponse(
                1, 
                10, 
                6, 
                1, 
                List.of(movie1, movie2, movie3, movie4, movie5, movie6)
        );
    }

    @Test
    void shouldReturnEmptyListForNegativeThreshold() {
        // When
        Mono<List<String>> result = directorService.getDirectorsAboveThreshold(-1);

        // Then
        StepVerifier.create(result)
                .expectNext(List.of())
                .verifyComplete();
    }

    @Test
    void shouldReturnDirectorsAboveThreshold() {
        // Given
        when(movieRepository.getMoviesByPage(1)).thenReturn(Mono.just(singlePageResponse));

        // When
        Mono<List<String>> result = directorService.getDirectorsAboveThreshold(1);

        // Then
        StepVerifier.create(result)
                .expectNext(List.of("Martin Scorsese", "Woody Allen"))
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyListWhenNoDirectorsAboveThreshold() {
        // Given
        when(movieRepository.getMoviesByPage(1)).thenReturn(Mono.just(singlePageResponse));

        // When
        Mono<List<String>> result = directorService.getDirectorsAboveThreshold(5);

        // Then
        StepVerifier.create(result)
                .expectNext(List.of())
                .verifyComplete();
    }
} 