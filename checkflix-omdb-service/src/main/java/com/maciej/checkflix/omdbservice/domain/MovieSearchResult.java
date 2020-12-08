package com.maciej.checkflix.omdbservice.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "MOVIE_SEARCH_RESULTS")
public class MovieSearchResult {
    @Id
    @GeneratedValue
    @Column(name = "SEARCH_ID")
    private Long id;

    @NotNull
    @Column(name = "SEARCH_DATE")
    private LocalDateTime searchOn;

    @NotNull
    @Column(name = "SEARCH_TITLE")
    private String searchTitle;

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "JOIN_SEARCH_MOVIE",
            joinColumns = {@JoinColumn(name = "SEARCH_ID", referencedColumnName = "SEARCH_ID")},
            inverseJoinColumns = {@JoinColumn(name = "MOVIE_ID", referencedColumnName = "MOVIE_ID")}
    )
    private List<Movie> movies = new ArrayList<>();

    public MovieSearchResult(@NotNull LocalDateTime searchOn, @NotNull String searchTitle) {
        this.searchOn = searchOn;
        this.searchTitle = searchTitle;
    }

    public void addMovies(Movie... movies) {
        this.movies.addAll(Arrays.asList(movies));
        for (Movie movie: movies) {
            movie.getSearches().add(this);
        }
    }

    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        for (Movie movie: movies) {
            movie.getSearches().add(this);
        }
    }
}
