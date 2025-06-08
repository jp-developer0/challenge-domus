# Domus Challenge Solution

## Architecture Overview

This solution implements a reactive REST API using Spring WebFlux to retrieve directors who have directed more movies than a specified threshold. The application follows clean architecture principles and SOLID design principles to ensure maintainability and testability.

## Design Decisions

### Reactive Programming

The solution leverages Spring WebFlux and Project Reactor to provide a non-blocking, reactive implementation that efficiently handles concurrent requests. This approach was chosen over traditional Spring MVC with RestTemplate because:

1. **Scalability**: Reactive programming handles more concurrent connections with fewer threads.
2. **Efficient resource utilization**: Non-blocking I/O results in better resource usage.
3. **Backpressure**: Reactor naturally handles backpressure, preventing overwhelming downstream services.

### Pagination Handling

The external API is paginated, so I implemented an intelligent pagination strategy that:

1. First fetches page 1 to determine the total number of pages
2. Then uses reactive Flux to concurrently fetch all remaining pages
3. Combines all results and processes them efficiently

This approach is more efficient than sequential fetching of pages, especially when there are many pages.

### Clean Architecture

The application is structured according to clean architecture principles:

1. **Domain layer**: Contains the core business entities (Movie, MovieResponse, DirectorsResponse)
2. **Repository layer**: Abstracts data access with an interface (MovieRepository) and implementation (MovieRepositoryImpl)
3. **Service layer**: Contains business logic for aggregating and filtering directors
4. **Controller layer**: Handles HTTP requests and validation

### SOLID Principles

1. **Single Responsibility Principle**: Each class has a single responsibility (e.g., the repository handles data access, the service handles business logic)
2. **Open/Closed Principle**: The code is open for extension but closed for modification through the use of interfaces
3. **Liskov Substitution Principle**: Implementations can be substituted for their interfaces
4. **Interface Segregation Principle**: Interfaces are specific to client needs
5. **Dependency Inversion Principle**: High-level modules depend on abstractions, not concrete implementations

### Error Handling

The application includes comprehensive error handling:

1. Validates input parameters in the controller
2. Returns appropriate HTTP status codes for different error conditions (400 for invalid input, 500 for server errors)
3. Uses Reactor's error handling operators like onErrorResume to handle exceptions gracefully

### Documentation

The API is fully documented using OpenAPI (Swagger) annotations, providing:

1. Detailed endpoint descriptions
2. Parameter descriptions
3. Response types and status codes
4. Example responses

## Testing Strategy

The application includes comprehensive unit tests for both the service and controller layers:

1. **Service tests**: Test the business logic for different threshold values
2. **Controller tests**: Test HTTP request handling, validation, and responses

## Future Enhancements

1. **Caching**: Add caching to reduce API calls for repeated threshold values
2. **Rate limiting**: Implement rate limiting to protect the external API
3. **Circuit breaker**: Add a circuit breaker pattern to handle external API failures gracefully
4. **Metrics and monitoring**: Add metrics collection for monitoring performance 