package com.maciej.checkflix.tmdbservice.controller;

import com.maciej.checkflix.tmdbservice.domain.providersearch.CountryResultDto;
import com.maciej.checkflix.tmdbservice.domain.providersearch.ProviderDetailsDto;
import com.maciej.checkflix.tmdbservice.domain.reviews.AuthorDetailsDto;
import com.maciej.checkflix.tmdbservice.domain.reviews.ReviewResultDto;
import com.maciej.checkflix.tmdbservice.service.TmdbService;
import com.maciej.checkflix.tmdbservice.service.util.SupportedCountries;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
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
@WebMvcTest(TmdbController.class)
public class TmdbControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TmdbService tmdbService;

    @Test
    public void getMoviesBy() throws Exception {
        // Given
        ProviderDetailsDto flatrateDetails = new ProviderDetailsDto(1,
                "flatrateLogoPath", 1, "flatrateProvider");
        ProviderDetailsDto rentDetails = new ProviderDetailsDto(3,
                "rentLogoPath", 3, "rentProvider");
        ProviderDetailsDto buyDetails = new ProviderDetailsDto(2,
                "buyLogoPath", 2, "buyProvider");
        CountryResultDto searchResults = new CountryResultDto(
                "testLink", Arrays.asList(flatrateDetails),
                Arrays.asList(rentDetails), Arrays.asList(buyDetails));

        when(tmdbService.getProvidersFor("testImdbId", "testCountryName")).thenReturn(searchResults);

        // When & Then
        mockMvc.perform(get("/v1/providers?movieImdbId=testImdbId&countryName=testCountryName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link", is("testLink")))
                .andExpect(jsonPath("$.flatrate", hasSize(1)))
                .andExpect(jsonPath("$.flatrate[0].display_priority", is(1)))
                .andExpect(jsonPath("$.flatrate[0].logo_path", is("flatrateLogoPath")))
                .andExpect(jsonPath("$.flatrate[0].provider_id", is(1)))
                .andExpect(jsonPath("$.flatrate[0].provider_name", is("flatrateProvider")))
                .andExpect(jsonPath("$.rent", hasSize(1)))
                .andExpect(jsonPath("$.rent[0].display_priority", is(3)))
                .andExpect(jsonPath("$.rent[0].logo_path", is("rentLogoPath")))
                .andExpect(jsonPath("$.rent[0].provider_id", is(3)))
                .andExpect(jsonPath("$.rent[0].provider_name", is("rentProvider")))
                .andExpect(jsonPath("$.buy", hasSize(1)))
                .andExpect(jsonPath("$.buy[0].display_priority", is(2)))
                .andExpect(jsonPath("$.buy[0].logo_path", is("buyLogoPath")))
                .andExpect(jsonPath("$.buy[0].provider_id", is(2)))
                .andExpect(jsonPath("$.buy[0].provider_name", is("buyProvider")));
    }

    @Test
    public void getSupportedProviderCountries() throws Exception {
        // Given
        List<String> requiredValues = Stream.of(SupportedCountries.values())
                .map(SupportedCountries::getCountryName)
                .collect(Collectors.toList());

        when(tmdbService.getSupportedCountries()).thenReturn(requiredValues);

        // When & Then
        mockMvc.perform(get("/v1/providers/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(requiredValues.size())))
                .andExpect(e -> {
                    for (String type: requiredValues) {
                        jsonPath("$", contains(type));
                    }
                });
    }

    @Test
    public void getReviewsFor() throws Exception {
        // Given
        ReviewResultDto review = new ReviewResultDto(
                "testAuthor",
                new AuthorDetailsDto("testName", "testUsername", "testAvatarPath", 1.0),
                "testContent", "testCreatedAt", "testId", "testUpdatedAt", "testUrl");

        when(tmdbService.getReviewsFor("testImdbId")).thenReturn(Arrays.asList(review));

        // When & Then
        mockMvc.perform(get("/v1/reviews?imdbId=testImdbId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].author", is("testAuthor")))
                .andExpect(jsonPath("$[0].author_details.name", is("testName")))
                .andExpect(jsonPath("$[0].author_details.username", is("testUsername")))
                .andExpect(jsonPath("$[0].author_details.avatar_path", is("testAvatarPath")))
                .andExpect(jsonPath("$[0].author_details.rating", is(1.0)))
                .andExpect(jsonPath("$[0].content", is("testContent")))
                .andExpect(jsonPath("$[0].created_at", is("testCreatedAt")))
                .andExpect(jsonPath("$[0].id", is("testId")))
                .andExpect(jsonPath("$[0].updated_at", is("testUpdatedAt")))
                .andExpect(jsonPath("$[0].url", is("testUrl")));
    }

    @Test
    public void getNameFor() throws Exception {
        // Given
        String name = "testName";

        when(tmdbService.findNameBy("testImdbId")).thenReturn(name);

        // When & Then
        mockMvc.perform(get("/v1/name/testImdbId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(name)));
    }
}