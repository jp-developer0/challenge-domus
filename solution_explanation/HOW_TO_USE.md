# How to Use and Demonstrate the Domus Challenge Application

This guide provides step-by-step instructions for running, testing, and demonstrating the Domus Directors API application.

## Prerequisites

- Java 17 or newer
- Maven 3.6 or newer
- Git (for cloning the repository)

## Getting Started

### Clone and Build the Application

1. Clone the repository:
   ```bash
   git clone <your-repository-url>
   cd <repository-directory>
   ```

2. Build the application using Maven:
   ```bash
   ./mvnw clean package
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on port 8080 by default.

## Using the API

### API Endpoint

The application exposes a single REST endpoint:

```
GET /api/directors?threshold=X
```

Where `X` is an integer representing the threshold number of movies. The API returns a list of directors who have directed **more than** the specified threshold number of movies, sorted alphabetically.

### Example Requests

Using curl:

```bash
# Get directors with more than 2 movies
curl -X GET "http://localhost:8080/api/directors?threshold=2"

# Example response:
# {"directors":["Martin Scorsese","Woody Allen"]}
```

Using a web browser:
1. Open your browser and navigate to:
   ```
   http://localhost:8080/api/directors?threshold=2
   ```

## Testing the Application

### Running Automated Tests

Run the automated tests using Maven:

```bash
./mvnw test
```

This will execute all unit tests, including service and controller tests.

### Manual Testing

For manual testing, you can:

1. Use curl commands as shown above
2. Use a tool like Postman
3. Use the Swagger UI (see below)

## Using Swagger Documentation

The application includes comprehensive Swagger (OpenAPI) documentation that allows interactive exploration and testing of the API.

1. Access the Swagger UI by opening your browser and navigating to:
   ```
   http://localhost:8080/swagger-ui.html
   ```

2. The Swagger UI provides:
   - Detailed API documentation
   - Request parameters and types
   - Response formats
   - The ability to execute API calls directly from the UI

3. To test the API using Swagger UI:
   - Expand the `/api/directors` endpoint
   - Click the "Try it out" button
   - Enter a threshold value (e.g., 2)
   - Click "Execute"
   - View the response

