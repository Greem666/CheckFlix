package com.maciej.checkflix.analytics.controller;

import com.maciej.checkflix.analytics.service.QuickChartIoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewsController.class)
public class ReviewsControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuickChartIoService quickChartIoService;

    @Test
    public void getPositiveReviewsWordCloud() throws Exception {
        // Given
        String imdbId = "testImdbId";
        byte[] image = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        when(quickChartIoService.getWordCloudForPositiveReviews(anyString())).thenReturn(image);

        // When & Then
        MvcResult result = mockMvc.perform(get("/v1/analyse/reviews/positive?imdbId="+imdbId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertArrayEquals(image, result.getResponse().getContentAsByteArray());
        verify(quickChartIoService, times(1)).getWordCloudForPositiveReviews(imdbId);
    }

    @Test
    public void getNeutralReviewsWordCloud() throws Exception {
        // Given
        String imdbId = "testImdbId";
        byte[] image = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        when(quickChartIoService.getWordCloudForNeutralReviews(anyString())).thenReturn(image);

        // When & Then
        MvcResult result = mockMvc.perform(get("/v1/analyse/reviews/neutral?imdbId="+imdbId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertArrayEquals(image, result.getResponse().getContentAsByteArray());
        verify(quickChartIoService, times(1)).getWordCloudForNeutralReviews(imdbId);
    }

    @Test
    public void getNegativeReviewsWordCloud() throws Exception {
        // Given
        String imdbId = "testImdbId";
        byte[] image = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        when(quickChartIoService.getWordCloudForNegativeReviews(anyString())).thenReturn(image);

        // When & Then
        MvcResult result = mockMvc.perform(get("/v1/analyse/reviews/negative?imdbId="+imdbId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertArrayEquals(image, result.getResponse().getContentAsByteArray());
        verify(quickChartIoService, times(1)).getWordCloudForNegativeReviews(imdbId);
    }

    @Test
    public void getReviewRatingsPieChart() throws Exception {
        // Given
        String imdbId = "testImdbId";
        byte[] image = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        when(quickChartIoService.getReviewRatingsPieChart(anyString())).thenReturn(image);

        // When & Then
        MvcResult result = mockMvc.perform(get("/v1/analyse/reviews/piechart?imdbId="+imdbId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertArrayEquals(image, result.getResponse().getContentAsByteArray());
        verify(quickChartIoService, times(1)).getReviewRatingsPieChart(imdbId);
    }

    @Test
    public void getReviewRatingsDistribution() throws Exception {
        // Given
        String imdbId = "testImdbId";
        byte[] image = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        when(quickChartIoService.getReviewRatingsDistribution(anyString())).thenReturn(image);

        // When & Then
        MvcResult result = mockMvc.perform(get("/v1/analyse/reviews/histogram?imdbId="+imdbId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertArrayEquals(image, result.getResponse().getContentAsByteArray());
        verify(quickChartIoService, times(1)).getReviewRatingsDistribution(imdbId);
    }
}