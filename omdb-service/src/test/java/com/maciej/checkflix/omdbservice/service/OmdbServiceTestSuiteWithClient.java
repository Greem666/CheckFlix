package com.maciej.checkflix.omdbservice.service;

import com.maciej.checkflix.omdbservice.client.OmdbClient;
import com.maciej.checkflix.omdbservice.domain.*;
import com.maciej.checkflix.omdbservice.mapper.MovieMapper;
import com.maciej.checkflix.omdbservice.repository.MovieRepository;
import com.maciej.checkflix.omdbservice.repository.MovieSearchResultsRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OmdbServiceTestSuiteWithClient {

    @InjectMocks
    private OmdbService omdbService;

    @Mock
    private OmdbClient omdbClient;

    @Mock
    private MovieMapper movieMapper;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieSearchResultsRepository movieSearchResultsRepository;


    @Test
    public void shouldReturnEmptyArrayWithNoSearchTitle() {
        // Given
        MovieDto movieDto = new MovieDto("testTitle", "testYear", "testImdbId", "movie", "testPoster");
        Movie movie = new Movie("testTitle", "testYear", "testImdbId", Type.MOVIE, "testPoster");
        MovieNameSearchDto searchResult = new MovieNameSearchDto(Arrays.asList(movieDto), "1", "ok");

        when(movieSearchResultsRepository.findBySearchTitleEquals("testTitle")).thenReturn(new ArrayList<>());

        when(movieMapper.mapToMovieList(Collections.singletonList(movieDto))).thenReturn(Collections.singletonList(movie));

        when(omdbClient.findMoviesByName(eq("testTitle"))).thenReturn(searchResult);
        when(omdbClient.findMoviesByNameAndType(eq("testTitle"), anyString())).thenReturn(searchResult);
        when(omdbClient.findMoviesByNameAndYear(eq("testTitle"), anyString())).thenReturn(searchResult);
        when(omdbClient.findMoviesByNameAndYearAndType(eq("testTitle"), anyString(), anyString())).thenReturn(searchResult);

        // When
        List<MovieDto> resultsNameOnly = omdbService.findMovie("testTitle", "", "");
        List<MovieDto> resultsNameType = omdbService.findMovie("testTitle", "", "movie");
        List<MovieDto> resultsNameYear = omdbService.findMovie("testTitle", "testYear", "");
        List<MovieDto> resultsNameTypeYear = omdbService.findMovie("testTitle", "testType", "movie");

        // Then
        for (List<MovieDto> resultsSet: Arrays.asList(resultsNameOnly, resultsNameType, resultsNameYear, resultsNameTypeYear)) {
            for (MovieDto resultMovie: resultsSet) {
                Assert.assertEquals("testTitle", resultMovie.getTitle());
                Assert.assertEquals("testYear", resultMovie.getYear());
                Assert.assertEquals("testImdbId", resultMovie.getImdbID());
                Assert.assertEquals("movie", resultMovie.getType());
                Assert.assertEquals("testPoster", resultMovie.getPoster());
            }
        }
    }



}