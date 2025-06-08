package domus.challenge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import domus.challenge.model.DirectorsResponse;
import domus.challenge.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @Operation(summary = "Get directors with movie count above threshold", 
            description = "Returns a list of directors who have directed more movies than the specified threshold, sorted alphabetically")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved directors",
                    content = @Content(schema = @Schema(implementation = DirectorsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid threshold value"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/directors")
    public Mono<ResponseEntity<DirectorsResponse>> getDirectorsAboveThreshold(
            @Parameter(description = "Threshold value (must be a number)", required = true)
            @RequestParam("threshold") String thresholdParam) {
        
        try {
            int threshold = Integer.parseInt(thresholdParam);
            return directorService.getDirectorsAboveThreshold(threshold)
                    .map(directors -> ResponseEntity.ok(new DirectorsResponse(directors)))
                    .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
        } catch (NumberFormatException e) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Threshold must be a number"));
        }
    }
} 