package com.maciej.checkflix.repository;

import com.maciej.checkflix.domain.omdb.Movie;
import com.maciej.checkflix.domain.omdb.Type;
import com.maciej.checkflix.repository.omdb.MovieRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieRepositoryTestSuite {

    @Autowired
    private MovieRepository movieRepository;

    private Movie testMovie1;
    private Movie testMovie2;
    private Movie testMovie3;

    @Before
    public void beforeTest() {
        testMovie1 = new Movie(
                "Test title 1", "Test year 1", "Test imdbID 1", Type.MOVIE, "Test poster 1");
        testMovie2 = new Movie(
                "Test title 2", "Test year 2", "Test imdbID 2", Type.EPISODE, "Test poster 2");
        testMovie3 = new Movie(
                "Test title 3", "Test year 3", "Test imdbID 3", Type.SERIES, "Test poster 3");

        movieRepository.saveAll(Arrays.asList(testMovie1, testMovie2, testMovie3));
    }

    @After
    public void afterTest() {
        for (Movie testMovie: Arrays.asList(testMovie1, testMovie2, testMovie3)) {
            try {
                movieRepository.deleteById(testMovie.getId());
            } catch (IllegalArgumentException e) {
                // Do nothing
            }
        }
    }

    private void assertEquality(Movie expectedMovie, Movie checkedMovie) {
        Assert.assertEquals(expectedMovie.getTitle(), checkedMovie.getTitle());
        Assert.assertEquals(expectedMovie.getYear(), checkedMovie.getYear());
        Assert.assertEquals(expectedMovie.getImdbID(), checkedMovie.getImdbID());
        Assert.assertEquals(expectedMovie.getType(), checkedMovie.getType());
        Assert.assertEquals(expectedMovie.getPoster(), checkedMovie.getPoster());
    }

    @Test
    public void shouldCreateAndDeleteMovie() {
        // Given
        Movie newMovie = new Movie(
                "Test title 4", "Test year 4", "Test imdbID 4", Type.MOVIE, "Test poster 4");

        // When
        movieRepository.save(newMovie);
        Optional<Movie> readMovie = movieRepository.findById(newMovie.getId());

        // Then
        Assert.assertEquals(4, ((List<Movie>)movieRepository.findAll()).size());
        Assert.assertTrue(readMovie.isPresent());
        assertEquality(newMovie, readMovie.get());

        // Clean-up
        movieRepository.deleteById(newMovie.getId());
        Assert.assertFalse(movieRepository.existsById(newMovie.getId()));
    }

    @Test
    public void shouldReadMovie() {
        // Given & When
        Optional<Movie> readMovie = movieRepository.findById(testMovie1.getId());

        // Then
        Assert.assertTrue(readMovie.isPresent());
        assertEquality(testMovie1, readMovie.get());
    }

    @Test
    public void shouldUpdateMovie() {
        // Given
        String updatedTitle = "Updated Test title 2";
        String updatedYear = "Updated Test year 2";

        // When
        testMovie2.setTitle(updatedTitle);
        testMovie2.setYear(updatedYear);
        movieRepository.save(testMovie2);

        Optional<Movie> updatedTestMovie2 = movieRepository.findById(testMovie2.getId());

        // Then
        Assert.assertTrue(updatedTestMovie2.isPresent());
        Assert.assertEquals(updatedTitle, updatedTestMovie2.get().getTitle());
        Assert.assertEquals(updatedYear, updatedTestMovie2.get().getYear());
    }
}