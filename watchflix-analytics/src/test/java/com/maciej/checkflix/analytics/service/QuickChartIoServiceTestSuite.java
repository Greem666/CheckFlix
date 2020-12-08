package com.maciej.checkflix.analytics.service;

import com.maciej.checkflix.analytics.client.ImdbIdClient;
import com.maciej.checkflix.analytics.client.QuickChartIoClient;
import com.maciej.checkflix.analytics.domain.reviews.ImdbReviewDto;
import com.maciej.checkflix.analytics.service.util.TextCleaner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class QuickChartIoServiceTestSuite {

    @InjectMocks
    private QuickChartIoService quickChartIoService;

    @Mock
    private ImdbIdClient imdbIdClient;

    @Mock
    private QuickChartIoClient quickChartIoClient;

    @Mock
    private TextCleaner textCleaner;

    private ImdbReviewDto positiveReview;
    private ImdbReviewDto neutralReview;
    private ImdbReviewDto negativeReview;

    @Before
    public void setUp() throws Exception {
        positiveReview = new ImdbReviewDto(10, "testTitlePositive",
                "testUserNamePositive", "testDatePositive", "testReviewPositive");
        neutralReview = new ImdbReviewDto(7, "testTitleNeutral",
                "testUserNameNeutral", "testDateNeutral", "testReviewNeutral");
        negativeReview = new ImdbReviewDto(3, "testTitleNegative",
                "testUserNameNegative", "testDateNegative", "testReviewNegative");
    }

    @Test
    public void getWordCloudForPositiveReviews() {
        // Given
        String imdbId = "testImdbId";
        String text = "testReviewPositive";
        byte[] positiveImageOutput = {1, 2, 3, 4, 5};

        when(imdbIdClient.getReviewsFor(imdbId)).thenReturn(Arrays.asList(positiveReview, neutralReview, negativeReview));
        when(textCleaner.cleanText(text)).thenReturn(text);
        when(quickChartIoClient.getWordCloudForPositiveText(text)).thenReturn(positiveImageOutput);

        // When
        byte[] image = quickChartIoService.getWordCloudForPositiveReviews(imdbId);

        // Then
        Assert.assertEquals(5, image.length);

        verify(imdbIdClient, times(1)).getReviewsFor(imdbId);
        verify(textCleaner, times(1)).cleanText(text);
        verify(quickChartIoClient, times(1)).getWordCloudForPositiveText(anyString());
    }

    @Test
    public void getWordCloudForNeutralReviews() {
        // Given
        String imdbId = "testImdbId";
        String text = "testReviewNeutral";
        byte[] neutralImageOutput = {1, 2, 3, 4, 5, 6, 7};

        when(imdbIdClient.getReviewsFor(imdbId)).thenReturn(Arrays.asList(positiveReview, neutralReview, negativeReview));
        when(textCleaner.cleanText(text)).thenReturn(text);
        when(quickChartIoClient.getWordCloudForNeutralText(text)).thenReturn(neutralImageOutput);

        // When
        byte[] image = quickChartIoService.getWordCloudForNeutralReviews(imdbId);

        // Then
        Assert.assertEquals(7, image.length);

        verify(imdbIdClient, times(1)).getReviewsFor(imdbId);
        verify(textCleaner, times(1)).cleanText(text);
        verify(quickChartIoClient, times(1)).getWordCloudForNeutralText(anyString());
    }

    @Test
    public void getWordCloudForNegativeReviews() {
        // Given
        String imdbId = "testImdbId";
        String text = "testReviewNegative";
        byte[] negativeImageOutput = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        when(imdbIdClient.getReviewsFor(imdbId)).thenReturn(Arrays.asList(positiveReview, neutralReview, negativeReview));
        when(textCleaner.cleanText(text)).thenReturn(text);
        when(quickChartIoClient.getWordCloudForNegativeText(text)).thenReturn(negativeImageOutput);

        // When
        byte[] image = quickChartIoService.getWordCloudForNegativeReviews(imdbId);

        // Then
        Assert.assertEquals(10, image.length);

        verify(imdbIdClient, times(1)).getReviewsFor(imdbId);
        verify(textCleaner, times(1)).cleanText(text);
        verify(quickChartIoClient, times(1)).getWordCloudForNegativeText(anyString());
    }

    @Test
    public void getReviewRatingsPieChart() {
        // Given
        String imdbId = "testImdbId";
        byte[] imageOutput = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        when(imdbIdClient.getReviewsFor(imdbId)).thenReturn(Arrays.asList(positiveReview, neutralReview, negativeReview));
        when(quickChartIoClient.getReviewRatingsPieChart(
                Arrays.asList("Positive reviews", "Neutral reviews", "Negative reviews"),
                Arrays.asList(1, 1, 1)
        )).thenReturn(imageOutput);

        // When
        byte[] image = quickChartIoService.getReviewRatingsPieChart(imdbId);

        // Then
        Assert.assertEquals(10, image.length);

        verify(imdbIdClient, times(1)).getReviewsFor(imdbId);
        verify(quickChartIoClient, times(1)).getReviewRatingsPieChart(any(), any());
    }

    @Test
    public void getReviewRatingsDistribution() {
        // Given
        String imdbId = "testImdbId";
        byte[] imageOutput = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        when(imdbIdClient.getReviewsFor(imdbId)).thenReturn(Arrays.asList(positiveReview, neutralReview, negativeReview));
        when(quickChartIoClient.getReviewRatingsDistribution(
                Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
                Arrays.asList(0, 0, 1, 0, 0, 0, 1, 0, 0, 1))
        ).thenReturn(imageOutput);

        // When
        byte[] image = quickChartIoService.getReviewRatingsDistribution(imdbId);

        // Then
        Assert.assertEquals(15, image.length);

        verify(imdbIdClient, times(1)).getReviewsFor(imdbId);
        verify(quickChartIoClient, times(1)).getReviewRatingsDistribution(any(), any());
    }
}