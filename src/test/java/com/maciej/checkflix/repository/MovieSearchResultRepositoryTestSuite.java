package com.maciej.checkflix.repository;

import com.maciej.checkflix.domain.omdb.Movie;
import com.maciej.checkflix.domain.omdb.MovieSearchResult;
import com.maciej.checkflix.domain.omdb.Type;
import com.maciej.checkflix.repository.omdb.MovieRepository;
import com.maciej.checkflix.repository.omdb.MovieSearchResultsRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieSearchResultRepositoryTestSuite {

    @Autowired
    private MovieSearchResultsRepository movieSearchResultsRepository;

    @Autowired
    private MovieRepository movieRepository;

    private static Movie testMovie1;
    private static Movie testMovie2;
    private static Movie testMovie3;

    private static MovieSearchResult searchResults1;
    private static MovieSearchResult searchResults2;

    private LocalDateTime searchTime = LocalDateTime.now();

    @Before
    public void beforeTest() {
        testMovie1 = new Movie(
                "Test title 1", "Test year 1", "Test imdbID 1", Type.MOVIE, "Test poster 1");
        testMovie2 = new Movie(
                "Test title 2", "Test year 2", "Test imdbID 2", Type.EPISODE, "Test poster 2");
        testMovie3 = new Movie(
                "Test title 3", "Test year 3", "Test imdbID 3", Type.SERIES, "Test poster 3");

        searchResults1 = new MovieSearchResult(searchTime, "test title 1");
        searchResults2 = new MovieSearchResult(searchTime, "test title 2");

        searchResults1.addMovies(testMovie1, testMovie2);
        searchResults2.addMovies(testMovie2, testMovie3);

        movieRepository.saveAll(Arrays.asList(testMovie1, testMovie2, testMovie3));
        movieSearchResultsRepository.saveAll(Arrays.asList(searchResults1, searchResults2));
    }

    @After
    public void afterTest() {
        List<MovieSearchResult> testSearchList = Arrays.asList(searchResults1, searchResults2);
        for (MovieSearchResult testSearch: testSearchList) {
            movieSearchResultsRepository.deleteById(testSearch.getId());
        }

        List<Movie> testMovieList = Arrays.asList(testMovie1, testMovie2, testMovie3);
        for (Movie testMovie: testMovieList) {
            movieRepository.deleteById(testMovie.getId());
        }
    }

    @Test
    public void shouldReadMovieSearchResult() {
        // Given & When
        Optional<MovieSearchResult> retrievedSearchResult1 = movieSearchResultsRepository.findById(searchResults1.getId());
        Optional<MovieSearchResult> retrievedSearchResult2 = movieSearchResultsRepository.findById(searchResults2.getId());

        // Then
        Assert.assertTrue(retrievedSearchResult1.isPresent());
        Assert.assertEquals("test title 1", retrievedSearchResult1.get().getSearchTitle());
        Assert.assertTrue(retrievedSearchResult1.get().getMovies().containsAll(Arrays.asList(testMovie1, testMovie2)));

        Assert.assertTrue(retrievedSearchResult2.isPresent());
        Assert.assertEquals("test title 2", retrievedSearchResult2.get().getSearchTitle());
        Assert.assertTrue(retrievedSearchResult2.get().getMovies().containsAll(Arrays.asList(testMovie2, testMovie3)));
    }

    @Test
    public void shouldAddAndDeleteMovieSearchResult() {
        // Given
        MovieSearchResult searchResults3 = new MovieSearchResult(searchTime, "test title 3");
        searchResults3.addMovies(testMovie1, testMovie3);

        movieSearchResultsRepository.save(searchResults3);

        // When
        Optional<MovieSearchResult> retrievedSearchResult3 = movieSearchResultsRepository.findById(searchResults3.getId());

        // Then
        Assert.assertTrue(retrievedSearchResult3.isPresent());
        Assert.assertEquals("test title 3", retrievedSearchResult3.get().getSearchTitle());
        Assert.assertTrue(retrievedSearchResult3.get().getMovies().containsAll(Arrays.asList(testMovie1, testMovie3)));

        // Clean-up
        try {
            movieSearchResultsRepository.deleteById(searchResults3.getId());
        } catch (IllegalArgumentException e) {
            // Do nothing
        }
        Assert.assertFalse(movieSearchResultsRepository.existsById(searchResults3.getId()));
    }

    @Test
    public void shouldUpdateMovieSearchResult() {
        // Given
        String newTitle = "Updated search title";

        searchResults1.addMovies(testMovie3);
        searchResults1.setSearchTitle(newTitle);

        movieSearchResultsRepository.save(searchResults1);

        // When
        Optional<MovieSearchResult> retrievedSearchResult1 = movieSearchResultsRepository
                .findById(searchResults1.getId());

        // Then
        Assert.assertTrue(retrievedSearchResult1.isPresent());
        Assert.assertEquals(newTitle, retrievedSearchResult1.get().getSearchTitle());
        Assert.assertTrue(retrievedSearchResult1.get().getMovies().containsAll(
                Arrays.asList(testMovie1, testMovie2, testMovie3)));
    }
}