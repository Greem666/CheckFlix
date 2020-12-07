package com.maciej.checkflix.webscraper.controller;

import com.maciej.checkflix.webscraper.domain.ImdbReviewDto;
import com.maciej.checkflix.webscraper.service.ImdbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ImdbController.class)
public class ImdbControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImdbService imdbService;

    @Test
    public void getImdbReviewsFor() throws Exception {
        // Given
        ImdbReviewDto review = new ImdbReviewDto(1, "testTitle", "testUsername", "testDate", "testReview");

        when(imdbService.getReviewsHtmlPageFor("testImdbId")).thenReturn(
                Collections.singletonList(review));

        // When & Then
        mockMvc.perform(get("/v1/imdb/reviews?imdbId=testImdbId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].rating", is(1)))
                .andExpect(jsonPath("$[0].title", is("testTitle")))
                .andExpect(jsonPath("$[0].username", is("testUsername")))
                .andExpect(jsonPath("$[0].date", is("testDate")))
                .andExpect(jsonPath("$[0].review", is("testReview")));
    }

    @Test
    public void prepareCachedImdbReviewsFor() throws Exception {
        // Given
        String imdbId = "testImdbId";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/imdb/reviews/cache?imdbId="+imdbId))
                .andExpect(status().isOk());
        verify(imdbService, times(1)).prepareCachedImdbReviewsFor(imdbId);
    }
}