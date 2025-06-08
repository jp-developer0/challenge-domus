package domus.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @JsonProperty("Title")
    private String title;
    
    @JsonProperty("Year")
    private String year;
    
    @JsonProperty("Rated")
    private String rated;
    
    @JsonProperty("Released")
    private String released;
    
    @JsonProperty("Runtime")
    private String runtime;
    
    @JsonProperty("Genre")
    private String genre;
    
    @JsonProperty("Director")
    private String director;
    
    @JsonProperty("Writer")
    private String writer;
    
    @JsonProperty("Actors")
    private String actors;
} 