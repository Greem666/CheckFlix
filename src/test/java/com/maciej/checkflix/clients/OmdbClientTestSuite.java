package com.maciej.checkflix.clients;


import com.maciej.checkflix.config.OmdbConfig;
import com.maciej.checkflix.domain.omdb.MovieDto;
import com.maciej.checkflix.domain.omdb.MovieNameSearchDto;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OmdbClientTestSuite {

    @InjectMocks
    private OmdbClient omdbClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private OmdbConfig omdbConfig;

    private static MovieDto movie;
    private static MovieNameSearchDto movieSearchResults;

    @BeforeClass
    public static void beforeAllTests() {
        movie = new MovieDto(
                "testTitle","testYear","testID","testType","testPostedUrl"
        );
        movieSearchResults = new MovieNameSearchDto(
                Collections.singletonList(movie),
                "100",
                "True"
        );
    }

    private void doAssertions(MovieNameSearchDto resultMovies) {
        Assert.assertEquals(movie.getImdbID(), resultMovies.getMovieDtoList().get(0).getImdbID());
        Assert.assertEquals(movie.getTitle(), resultMovies.getMovieDtoList().get(0).getTitle());
        Assert.assertEquals(movie.getType(), resultMovies.getMovieDtoList().get(0).getType());
        Assert.assertEquals(movie.getYear(), resultMovies.getMovieDtoList().get(0).getYear());
        Assert.assertEquals(movie.getPoster(), resultMovies.getMovieDtoList().get(0).getPoster());

        Assert.assertEquals(movieSearchResults.getTotalResults(), resultMovies.getTotalResults());
        Assert.assertEquals(movieSearchResults.getResponse(), resultMovies.getResponse());
    }

    @Test
    public void shouldReturnMoviesByName() {
        // Given
        String testTitle = "testTitle";

        when(omdbConfig.getApiUrl()).thenReturn("https://test.com/");
        when(omdbConfig.getApiKey()).thenReturn("testKey");

        when(restTemplate.getForObject(URI.create("https://test.com/?apikey=testKey&s="+testTitle), MovieNameSearchDto.class))
                .thenReturn(movieSearchResults);

        // When
        MovieNameSearchDto resultMovies = omdbClient.findMoviesByName(testTitle);

        // Then
        doAssertions(resultMovies);
        verify(restTemplate, times(1)).getForObject(any(), ArgumentMatchers.any());
    }

    @Test
    public void shouldReturnMoviesByNameAndYear() {
        // Given
        String testTitle = "testTitle";
        String testYear = "testYear";

        when(omdbConfig.getApiUrl()).thenReturn("https://test.com/");
        when(omdbConfig.getApiKey()).thenReturn("testKey");

        when(restTemplate.getForObject(URI.create("https://test.com/?apikey=testKey&s="+testTitle+"&y="+testYear), MovieNameSearchDto.class))
                .thenReturn(movieSearchResults);

        // When
        MovieNameSearchDto resultMovies = omdbClient.findMoviesByNameAndYear(testTitle, testYear);

        // Then
        doAssertions(resultMovies);
        verify(restTemplate, times(1)).getForObject(any(), ArgumentMatchers.any());
    }

    @Test
    public void shouldReturnMoviesByNameAndYearAndType() {
        // Given
        String testTitle = "testTitle";
        String testYear = "testYear";
        String testType = "testType";

        when(omdbConfig.getApiUrl()).thenReturn("https://test.com/");
        when(omdbConfig.getApiKey()).thenReturn("testKey");

        when(
                restTemplate.getForObject(
                        URI.create("https://test.com/?apikey=testKey&s="+testTitle+"&y="+testYear+"&type="+testType),
                        MovieNameSearchDto.class)
        ).thenReturn(movieSearchResults);

        // When
        MovieNameSearchDto resultMovies = omdbClient.findMoviesByNameAndYearAndType(testTitle, testYear, testType);

        // Then
        doAssertions(resultMovies);
        verify(restTemplate, times(1)).getForObject(any(), ArgumentMatchers.any());
    }

    @Test
    public void shouldReturnMoviesByNameAndType() {
        // Given
        String testTitle = "testTitle";
        String testType = "testType";

        when(omdbConfig.getApiUrl()).thenReturn("https://test.com/");
        when(omdbConfig.getApiKey()).thenReturn("testKey");

        when(
                restTemplate.getForObject(
                        URI.create("https://test.com/?apikey=testKey&s="+testTitle+"&type="+testType),
                        MovieNameSearchDto.class)
        ).thenReturn(movieSearchResults);

        // When
        MovieNameSearchDto resultMovies = omdbClient.findMoviesByNameAndType(testTitle, testType);

        // Then
        doAssertions(resultMovies);
        verify(restTemplate, times(1)).getForObject(any(), ArgumentMatchers.any());
    }
}