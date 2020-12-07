package com.maciej.checkflix.analytics.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class QuickChartIoClientTestSuite {

    @InjectMocks
    private QuickChartIoClient quickChartIoClient;

    @Mock
    private RestTemplate restTemplate;

    private String textInput;
    private byte[] expectedOutput;

    @Before
    public void setUp() throws Exception {
        textInput = "randomText";
        expectedOutput = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    }

    @Test
    public void getWordCloudForPositiveText() {
        // Given
        when(restTemplate.postForObject(any(), any(), eq(byte[].class))).thenReturn(expectedOutput);

        // When
        byte[] outputImage = quickChartIoClient.getWordCloudForPositiveText(textInput);

        // Then
        Assert.assertEquals(10, outputImage.length);

        verify(restTemplate, times(1)).postForObject(any(), any(), eq(byte[].class));
    }

    @Test
    public void getWordCloudForNeutralText() {
        // Given
        when(restTemplate.postForObject(any(), any(), eq(byte[].class))).thenReturn(expectedOutput);

        // When
        byte[] outputImage = quickChartIoClient.getWordCloudForNeutralText(textInput);

        // Then
        Assert.assertEquals(10, outputImage.length);

        verify(restTemplate, times(1)).postForObject(any(), any(), eq(byte[].class));
    }

    @Test
    public void getWordCloudForNegativeText() {
        // Given
        when(restTemplate.postForObject(any(), any(), eq(byte[].class))).thenReturn(expectedOutput);

        // When
        byte[] outputImage = quickChartIoClient.getWordCloudForNegativeText(textInput);

        // Then
        Assert.assertEquals(10, outputImage.length);

        verify(restTemplate, times(1)).postForObject(any(), any(), eq(byte[].class));
    }

    @Test
    public void getReviewRatingsPieChart() {
        // Given
        when(restTemplate.postForObject(any(), any(), eq(byte[].class))).thenReturn(expectedOutput);

        // When
        byte[] outputImage = quickChartIoClient.getReviewRatingsPieChart(
                Arrays.asList("testLabel1", "testLabel2"), Arrays.asList(1, 2));

        // Then
        Assert.assertEquals(10, outputImage.length);

        verify(restTemplate, times(1)).postForObject(any(), any(), eq(byte[].class));
    }

    @Test
    public void getReviewRatingsDistribution() {
        // Given
        when(restTemplate.postForObject(any(), any(), eq(byte[].class))).thenReturn(expectedOutput);

        // When
        byte[] outputImage = quickChartIoClient.getReviewRatingsDistribution(
                Arrays.asList("testLabel1", "testLabel2"), Arrays.asList(1, 2));

        // Then
        Assert.assertEquals(10, outputImage.length);

        verify(restTemplate, times(1)).postForObject(any(), any(), eq(byte[].class));
    }
}