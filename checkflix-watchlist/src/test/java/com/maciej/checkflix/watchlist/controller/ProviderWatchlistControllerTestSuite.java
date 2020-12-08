package com.maciej.checkflix.watchlist.controller;

import com.google.gson.Gson;
import com.maciej.checkflix.watchlist.domain.ProviderType;
import com.maciej.checkflix.watchlist.domain.ProviderWatchlist;
import com.maciej.checkflix.watchlist.domain.ProviderWatchlistDto;
import com.maciej.checkflix.watchlist.service.ProviderWatchlistService;
import com.maciej.checkflix.watchlist.service.TmdbService;
import com.maciej.checkflix.watchlist.service.util.SupportedCountries;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProviderWatchlistController.class)
public class ProviderWatchlistControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProviderWatchlistService providerWatchlistService;

    @Test
    public void getAllItemsOnProviderWatchlist() throws Exception {
        // Given
        ProviderWatchlistDto watchlistEntry = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail",
                "testImdbId", "testMovieName",
                "testCountry", "testProviderType");

        when(providerWatchlistService.getAllItemsOnWatchlist()).thenReturn(Collections.singletonList(watchlistEntry));

        // When & Then
        mockMvc.perform(get("/v1/watchlist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("testUserName")))
                .andExpect(jsonPath("$[0].email", is("testEmail")))
                .andExpect(jsonPath("$[0].imdbId", is("testImdbId")))
                .andExpect(jsonPath("$[0].movieName", is("testMovieName")))
                .andExpect(jsonPath("$[0].country", is("testCountry")))
                .andExpect(jsonPath("$[0].providerType", is("testProviderType")));
    }

    @Test
    public void getItemOnProviderWatchlist() throws Exception {
        // Given
        ProviderWatchlistDto watchlistEntry = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail",
                "testImdbId", "testMovieName",
                "testCountry", "testProviderType");

        when(providerWatchlistService.getItemOnWatchlist(1L)).thenReturn(watchlistEntry);

        // When & Then
        mockMvc.perform(get("/v1/watchlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("testUserName")))
                .andExpect(jsonPath("$.email", is("testEmail")))
                .andExpect(jsonPath("$.imdbId", is("testImdbId")))
                .andExpect(jsonPath("$.movieName", is("testMovieName")))
                .andExpect(jsonPath("$.country", is("testCountry")))
                .andExpect(jsonPath("$.providerType", is("testProviderType")));
    }

    @Test
    public void getItemOnProviderWatchlistBy() throws Exception {
        // Given
        ProviderWatchlistDto watchlistEntry = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail",
                "testImdbId", "testMovieName",
                "testCountry", "testProviderType");

        when(providerWatchlistService.getItemOnProviderWatchlistBy("testUserName")).thenReturn(
                Collections.singletonList(watchlistEntry));

        // When & Then
        mockMvc.perform(get("/v1/watchlist/search?phrase=testUserName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("testUserName")))
                .andExpect(jsonPath("$[0].email", is("testEmail")))
                .andExpect(jsonPath("$[0].imdbId", is("testImdbId")))
                .andExpect(jsonPath("$[0].movieName", is("testMovieName")))
                .andExpect(jsonPath("$[0].country", is("testCountry")))
                .andExpect(jsonPath("$[0].providerType", is("testProviderType")));
    }

    @Test
    public void addItemToProviderWatchlist() throws Exception {
        // Given
        ProviderWatchlistDto watchlistEntry = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail",
                "testImdbId", "testMovieName",
                "testCountry", "testProviderType");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(watchlistEntry);

        // When & Then
        mockMvc.perform(post("/v1/watchlist")
                    .characterEncoding("UTF-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent))
                    .andExpect(status().isOk());
        verify(providerWatchlistService, times(1)).addNewItemToWatchlist(
                ArgumentMatchers.any(ProviderWatchlistDto.class));
    }

    @Test
    public void modifyItemOnProviderWatchlist() throws Exception {
        // Given
        ProviderWatchlistDto watchlistEntry = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail",
                "testImdbId", "testMovieName",
                "testCountry", "testProviderType");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(watchlistEntry);

        // When & Then
        mockMvc.perform(put("/v1/watchlist")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());
        verify(providerWatchlistService, times(1)).modifyItemOnWatchlist(
                ArgumentMatchers.any(ProviderWatchlistDto.class));
    }

    @Test
    public void removeItemFromProviderWatchlist() throws Exception {
        // Given
        ProviderWatchlistDto watchlistEntry = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail",
                "testImdbId", "testMovieName",
                "testCountry", "testProviderType");

        // When & Then
        mockMvc.perform(delete("/v1/watchlist/1"))
                .andExpect(status().isOk());
        verify(providerWatchlistService, times(1)).removeItemFromWatchlist(anyLong());
    }

    @Test
    public void getProviderTypes() throws Exception {
        // Given
        List<String> requiredValues = Stream.of(ProviderType.values())
                .map(ProviderType::getTmdbName)
                .collect(Collectors.toList());

        when(providerWatchlistService.getProviderTypes()).thenReturn(requiredValues);

        // When & Then
        mockMvc.perform(get("/v1/watchlist/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(requiredValues.size())))
                .andExpect(e -> {
                    for (String type: requiredValues) {
                        jsonPath("$", contains(type));
                    }
                });
    }
}