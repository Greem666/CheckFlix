package com.maciej.checkflix.mapper;

import com.maciej.checkflix.domain.omdb.Movie;
import com.maciej.checkflix.domain.omdb.MovieDto;
import com.maciej.checkflix.domain.omdb.Type;
import com.maciej.checkflix.mapper.omdb.MovieMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieMapperTestSuite {

    @Autowired
    private MovieMapper movieMapper;

    private static Movie movie;
    private static Movie movie2;
    private static MovieDto movieDto;
    private static MovieDto movieDto2;

    @BeforeClass
    public static void init() {
        movie = new Movie(
                1L, "Test title 1", "Test year 1", "Test imdbID 1",
                Type.MOVIE, "Test poster 1", new ArrayList<>());
        movie2 = new Movie(
                2L, "Test title 2", "Test year 2", "Test imdbID 2",
                Type.SERIES, "Test poster 2", new ArrayList<>());

        movieDto = new MovieDto(
                "Test title 1", "Test year 1", "Test imdbID 1",
                Type.MOVIE.getLabel(), "Test poster 1");
        movieDto2 = new MovieDto(
                "Test title 2", "Test year 2", "Test imdbID 2",
                Type.SERIES.getLabel(), "Test poster 2");
    }

    private void assertEquality(MovieDto movieDto1, MovieDto movieDto2) {
        Assert.assertEquals(movieDto1.getTitle(), movieDto2.getTitle());
        Assert.assertEquals(movieDto1.getYear(), movieDto2.getYear());
        Assert.assertEquals(movieDto1.getImdbID(), movieDto2.getImdbID());
        Assert.assertEquals(movieDto1.getType(), movieDto2.getType());
        Assert.assertEquals(movieDto1.getPoster(), movieDto2.getPoster());
    }

    private void assertEquality(Movie movie1, Movie movie2) {
        Assert.assertEquals(movie1.getTitle(), movie2.getTitle());
        Assert.assertEquals(movie1.getYear(), movie2.getYear());
        Assert.assertEquals(movie1.getImdbID(), movie2.getImdbID());
        Assert.assertEquals(movie1.getType(), movie2.getType());
        Assert.assertEquals(movie1.getPoster(), movie2.getPoster());
    }

    @Test
    public void mapToMovieDto() {
        // Given & When
        MovieDto newMovieDto = movieMapper.mapToMovieDto(movie);

        // Then
        assertEquality(movieDto, newMovieDto);
    }

    @Test
    public void mapToMovie() {
        // Given & When
        Movie newMovie = movieMapper.mapToMovie(movieDto);

        // Then
        assertEquality(movie, newMovie);
    }

    @Test
    public void mapToMovieDtoList() {
        // Given & When
        List<MovieDto> newMovieDtoList = movieMapper.mapToMovieDtoList(Arrays.asList(movie, movie2));

        // Then
        assertEquality(movieDto, newMovieDtoList.get(0));
        assertEquality(movieDto2, newMovieDtoList.get(1));
    }

    @Test
    public void mapToMovieList() {
        // Given & When
        List<Movie> newMovieList = movieMapper.mapToMovieList(Arrays.asList(movieDto, movieDto2));

        // Then
        assertEquality(movie, newMovieList.get(0));
        assertEquality(movie2, newMovieList.get(1));
    }
}