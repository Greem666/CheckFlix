package com.maciej.checkflix.domain.omdb;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "MOVIES")
public class Movie {
    @Id
    @GeneratedValue
    @Column(name = "MOVIE_ID")
    public Long id;

    @Column(name = "TITLE")
    public String title;

    @Column(name = "YEAR")
    public String year;

    @Column(name = "IMDB_ID")
    public String imdbID;

    @Column(name = "TYPE")
    public Type type;

    @Column(name = "POSTER_URL")
    public String poster;


    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "movies", cascade = CascadeType.MERGE)
    private List<MovieSearchResult> searches = new ArrayList<>();

    public Movie(String title, String year, String imdbID, Type type, String poster) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = poster;
    }

    public void addSearches(MovieSearchResult... searches) {
        this.searches.addAll(Arrays.asList(searches));
        for (MovieSearchResult search: searches) {
            search.getMovies().add(this);
        }
    }

    public void addSearches(List<MovieSearchResult> searches) {
        this.searches.addAll(searches);
        for (MovieSearchResult search: searches) {
            search.getMovies().add(this);
        }
    }
}
