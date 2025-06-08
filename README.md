# Domus Back-End Developer Challenge.

## Description 

In this challenge, the REST API contains information about a collection of movies released after the year 2010, directed by acclaimed directors.
Given a threshold value, your task is to use the API to retrieve a list of directors who have directed more movies than the specified threshold. Specifically, the API should return the names of directors whose movie count is strictly greater than the given threshold, sorted alphabetically.

To access the movie collection, perform an HTTP GET request to the following endpoint:

```
https://challenge.iugolabs.com/api/movies/search?page=<pageNumber>
```

where <pageNumber> is an integer denoting the page of the results to return.

The response to such request is a JSON with the following 5 fields:

- page: The current page of the results
- per_page: The maximum number of movies returned per page.
- total: The total number of movies on all pages of the result.
- total_pages: The total number of pages with results.
- data: An array of objects containing movies returned on the requested page

Each movie record has the following schema:

- Title: title of the movie
- Year: year the movie was released
- Rated: movie rating
- Released: movie release date
- Runtime: movie duration time in minutes
- Genre: move genre
- Director: movie director
- Writer: movie writer 
- Actors: movie actors  

##  Task

Fork the provided repository and implement a REST API endpoint using the provided template:

```
/api/directors?threshold=X
```

This endpoint must return a JSON object containing the names of directors whose number of movies directed is strictly greater than the given threshold.

The names should be returned in alphabetical order.

Sample : `/api/directors?threshold=4`

Json response:
```
{  "directors": ["Martin Scorsese","Woody Allen"] }
```

## Criteria

Some things we'll evaluate are:

- Correctness: The solution must return accurate results based on the given threshold. Ensure the route and query parameters are handled correctly.
- Fail proof: The solution should handle errors and edge cases gracefully. Negative threshold values should return an empty list. Non-number thresholds should return an error message.
- Tests: The solution should have tests.
- Prefer newer technologies such as WebFlux over traditional RestTemplate.
- Implement an intelligent solution for handling pagination.
- Include Swagger documentation with detailed endpoint descriptions.
- Documentation: Create an .md file explaining the solution and considerations.
- Use external libraries like Lombok to facilitate things.
- Correct use of Spring decorators such as @Service and @Autowired.

## Submission

1. Fork the repository as a public repository.
2. Implement the solution.
3. Link the repository to the person who sent you the challenge.

## Solution Implementation

This implementation offers a complete solution to the challenge using modern technologies and best practices:

### Key Features

- **Reactive Programming**: Built with Spring WebFlux for non-blocking, efficient processing
- **Intelligent Pagination**: Handles external API pagination with an optimized approach
- **Clean Architecture**: Well-separated concerns between controller, service, and repository layers
- **Comprehensive Testing**: Includes unit tests for service and controller layers
- **API Documentation**: Full Swagger/OpenAPI documentation
- **Error Handling**: Graceful handling of all error cases and edge conditions

### Technical Highlights

- Java 17 and Spring Boot 3.x
- Reactive programming with WebFlux and Reactor
- WebClient for reactive HTTP communication
- Proper model mapping with Jackson annotations
- Efficient director filtering and sorting
- Detailed solution documentation in the `solution_explanation` folder

### Notable Design Decisions

- Used a reactive approach to handle the paginated API efficiently
- Implemented caching to improve performance for repeated requests
- Created a clean separation of concerns with repository and service patterns
- Added thorough logging for troubleshooting and monitoring
- Included comprehensive error handling for all edge cases

See the `solution_explanation` folder for more detailed documentation on the implementation.