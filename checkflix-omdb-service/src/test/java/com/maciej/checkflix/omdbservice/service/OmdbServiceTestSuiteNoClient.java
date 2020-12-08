package com.maciej.checkflix.omdbservice.service;

import com.maciej.checkflix.omdbservice.client.OmdbClient;
import com.maciej.checkflix.omdbservice.domain.Movie;
import com.maciej.checkflix.omdbservice.domain.MovieDto;
import com.maciej.checkflix.omdbservice.domain.MovieSearchResult;
import com.maciej.checkflix.omdbservice.domain.Type;
import com.maciej.checkflix.omdbservice.mapper.MovieMapper;
import com.maciej.checkflix.omdbservice.repository.MovieRepository;
import com.maciej.checkflix.omdbservice.repository.MovieSearchResultsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OmdbServiceTestSuiteNoClient {

    @Autowired
    private OmdbService omdbService;
    @Autowired
    private MovieSearchResultsRepository movieSearchResultsRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void shouldReturnEmptyArrayWithNoSearchTitle() {
        // Given
        String searchTitle = "";

        // When
        List<MovieDto> result = omdbService.findMovie(searchTitle, "test", "test");

        // Then
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void shouldReturnPreviousMovieSearchResults() {
        // Given
        Movie movie = new Movie("testTitle", "testYear", "testImdbId", Type.MOVIE, "testPoster");
        MovieSearchResult searchResult = new MovieSearchResult(1L, LocalDateTime.now(), "testTitle", new ArrayList<>());
        searchResult.addMovies(movie);

        movieRepository.save(movie);
        movieSearchResultsRepository.save(searchResult);

        long movieId = movie.getId();
        long movieSearchId = searchResult.getId();

        // When
        List<MovieDto> result = omdbService.findMovie("testTitle", "testYear", "movie");

        // Then
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("testTitle", result.get(0).getTitle());
        Assert.assertEquals("testYear", result.get(0).getYear());
        Assert.assertEquals("testImdbId", result.get(0).getImdbID());
        Assert.assertEquals("movie", result.get(0).getType());
        Assert.assertEquals("testPoster", result.get(0).getPoster());

        // Clean-up
        try {
            movieSearchResultsRepository.deleteById(movieSearchId);
        } catch (Exception e) {
            // Do nothing
        }

        try {
            movieRepository.deleteById(movieId);
        } catch (Exception e) {
            // Do nothing
        }
    }

    @Test
    public void shouldReturnAllTypes() {
        // Given
        List<String> requiredValues = Stream.of(Type.values()).map(Type::getLabel).collect(Collectors.toList());

        // When
        List<String> returnedValues = omdbService.getAvailableTypes();

        // Then
        Assert.assertEquals(requiredValues.size(), returnedValues.size());
        for (String reqVal: requiredValues) {
            Assert.assertTrue(returnedValues.contains(reqVal));
        }
    }


}