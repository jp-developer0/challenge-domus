package domus.challenge.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<Movie> data;
} 