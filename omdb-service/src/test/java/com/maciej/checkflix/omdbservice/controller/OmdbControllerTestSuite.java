package com.maciej.checkflix.omdbservice.controller;

import com.maciej.checkflix.omdbservice.domain.MovieDetailsDto;
import com.maciej.checkflix.omdbservice.domain.MovieDto;
import com.maciej.checkflix.omdbservice.domain.RatingDto;
import com.maciej.checkflix.omdbservice.domain.Type;
import com.maciej.checkflix.omdbservice.service.OmdbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OmdbController.class)
public class OmdbControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OmdbService omdbService;

    @Test
    public void getMoviesBy() throws Exception {
        // Given
        List<MovieDto> resultMovies = new ArrayList<>();
        resultMovies.add(new MovieDto("testTitle", "testYear", "testImdbId", "testType", "testPoster"));

        when(omdbService.findMovie("testTitle", "testYear", "testType")).thenReturn(resultMovies);

        // When & Then
        mockMvc.perform(get("/v1/movies?name=testTitle&year=testYear&type=testType"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].Title", is("testTitle")))
                .andExpect(jsonPath("$[0].Year", is("testYear")))
                .andExpect(jsonPath("$[0].imdbID", is("testImdbId")))
                .andExpect(jsonPath("$[0].Type", is("testType")))
                .andExpect(jsonPath("$[0].Poster", is("testPoster")));
    }

    @Test
    public void getAvailableTypes() throws Exception {
        // Given
        List<String> requiredValues = Stream.of(Type.values()).map(Type::getLabel).collect(Collectors.toList());

        when(omdbService.getAvailableTypes()).thenReturn(requiredValues);

        // When & Then
        mockMvc.perform(get("/v1/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(requiredValues.size())))
                .andExpect(e -> {
                    for (String type: requiredValues) {
                        jsonPath("$", contains(type));
                    }
                });
    }

    @Test
    public void getMovieDetails() throws Exception {
//        MovieDetailsDto
        // Given
        MovieDetailsDto resultMovie = new MovieDetailsDto(
                "testTitle", "testYear", "testRated", "testReleased", "testRuntime",
                "testGenre", "testDirector","testWriter", "testActors", "testPlot",
                "testLanguage", "testCountry", "testAwards", "testPoster",
                new ArrayList<RatingDto>(), "testMetascore", "testImdbRating",
                "testImdbVotes", "testImdbId", "testType", "testDvd" ,"testBoxOffice",
                "testProduction", "testWebsite", "testResponse"
        );

        when(omdbService.findMovieDetailsBy("testImdbId")).thenReturn(resultMovie);

        // When & Then
        mockMvc.perform(get("/v1/movie?movieImdbId=testImdbId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Title", is("testTitle")))
                .andExpect(jsonPath("$.Year", is("testYear")))
                .andExpect(jsonPath("$.imdbID", is("testImdbId")))
                .andExpect(jsonPath("$.Type", is("testType")))
                .andExpect(jsonPath("$.Poster", is("testPoster")));
    }
}