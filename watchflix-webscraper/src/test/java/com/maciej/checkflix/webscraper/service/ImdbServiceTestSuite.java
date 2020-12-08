package com.maciej.checkflix.webscraper.service;

import com.maciej.checkflix.webscraper.domain.ImdbReview;
import com.maciej.checkflix.webscraper.domain.ImdbReviewDto;
import com.maciej.checkflix.webscraper.mapper.ImdbReviewMapper;
import com.maciej.checkflix.webscraper.repository.ImdbReviewRepository;
import com.maciej.checkflix.webscraper.service.util.ImdbReviewScraper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ImdbServiceTestSuite {

    @InjectMocks
    private ImdbService imdbService;

    @Mock
    private ImdbReviewRepository imdbReviewRepository;

    @Mock
    private ImdbReviewMapper imdbReviewMapper;

    @Mock
    private ImdbReviewScraper imdbReviewScraper;

    @Test
    public void shouldReturnCachedImdbReview() {
        // Given
        ImdbReview review = new ImdbReview(LocalDateTime.now(), "testImdbId", 1, "testTitle",
                "testUsername", LocalDate.of(2011, 1, 1),"testReview");
        ImdbReviewDto reviewDto = new ImdbReviewDto(1, "testTitle", "testUsername",
                "2011-1-1", "testReview");

        when(imdbReviewMapper.mapToImdbReviewDtoList(Collections.singletonList(review)))
                .thenReturn(Collections.singletonList(reviewDto));
        when(imdbReviewRepository.findByImdbIdOrderByScrapedOnDesc("testImdbId"))
                .thenReturn(Collections.singletonList(review));

        // When
        List<ImdbReviewDto> result = imdbService.getReviewsHtmlPageFor("testImdbId");

        // Then
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(1, result.get(0).getRating());
        Assert.assertEquals("testTitle", result.get(0).getTitle());
        Assert.assertEquals("testUsername", result.get(0).getUsername());
        Assert.assertEquals("2011-1-1", result.get(0).getDate());
        Assert.assertEquals("testReview", result.get(0).getReview());
    }

    @Test
    public void shouldReturnNewlyScrapedImdbReview() throws IOException {
        // Given
        ImdbReview reviewOld = new ImdbReview(LocalDateTime.now().minusDays(31), "testImdbId", 1, "testTitle",
                "testUsername", LocalDate.of(2011, 1, 1),"testReview");
        when(imdbReviewRepository.findByImdbIdOrderByScrapedOnDesc("testImdbId"))
                .thenReturn(Collections.singletonList(reviewOld));

        ImdbReview reviewOld2 = new ImdbReview(LocalDateTime.now().minusDays(31), "testImdbId", 1, "testTitle3",
                "testUsername3", LocalDate.of(2013, 3, 3),"testReview3");
        when(imdbReviewRepository.findByImdbId("testImdbId"))
                .thenReturn(Collections.singletonList(reviewOld2));

        ImdbReview reviewNew = new ImdbReview(LocalDateTime.now(), "testImdbId", 2, "testTitle2",
                "testUsername2", LocalDate.of(2012, 2, 2),"testReview2");
        when(imdbReviewMapper.mapToImdbReviewList(any(), eq("testImdbId"))).thenReturn(Collections.singletonList(reviewNew));

        ImdbReviewDto reviewDtoNew = new ImdbReviewDto(2, "testTitle2", "testUsername2",
                "2012-2-2", "testReview2");
        when(imdbReviewMapper.mapToImdbReviewDtoList(any())).thenReturn(Collections.singletonList(reviewDtoNew));


        // When
        List<ImdbReviewDto> result = imdbService.getReviewsHtmlPageFor("testImdbId");

        // Then
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(2, result.get(0).getRating());
        Assert.assertEquals("testTitle2", result.get(0).getTitle());
        Assert.assertEquals("testUsername2", result.get(0).getUsername());
        Assert.assertEquals("2012-2-2", result.get(0).getDate());
        Assert.assertEquals("testReview2", result.get(0).getReview());

        verify(imdbReviewRepository, times(1)).findByImdbIdOrderByScrapedOnDesc("testImdbId");
        verify(imdbReviewRepository, times(1)).deleteAllByImdbId("testImdbId");
        verify(imdbReviewScraper, times(1)).scrapeImdbReviews("testImdbId");
        verify(imdbReviewRepository, times(1)).save(any(ImdbReview.class));
        verify(imdbReviewMapper, times(1)).mapToImdbReviewDtoList(any());
    }

    @Test
    public void shouldCacheReviewsWithNoPreviousScrapes() throws IOException {
        // Given
        when(imdbReviewRepository.findByImdbIdOrderByScrapedOnDesc("testImdbId"))
                .thenReturn(new ArrayList<>());

        when(imdbReviewRepository.findByImdbId("testImdbId")).thenReturn(new ArrayList<>());

        ImdbReview review = new ImdbReview(LocalDateTime.now(), "testImdbId", 2, "testTitle",
                "testUsername", LocalDate.of(2012, 2, 2),"testReview");
        when(imdbReviewMapper.mapToImdbReviewList(any(), eq("testImdbId"))).thenReturn(Collections.singletonList(review));

        // When
        imdbService.prepareCachedImdbReviewsFor("testImdbId");

        // Then
        verify(imdbReviewRepository, times(1)).findByImdbIdOrderByScrapedOnDesc("testImdbId");
        verify(imdbReviewRepository, times(1)).deleteAllByImdbId("testImdbId");
        verify(imdbReviewScraper, times(1)).scrapeImdbReviews("testImdbId");
        verify(imdbReviewRepository, times(1)).save(any(ImdbReview.class));
    }

    @Test
    public void shouldCacheReviewsWithOldScrapes() throws IOException {
        // Given
        ImdbReview reviewOld = new ImdbReview(LocalDateTime.now().minusDays(31), "testImdbId", 1, "testTitle",
                "testUsername", LocalDate.of(2011, 1, 1),"testReview");
        when(imdbReviewRepository.findByImdbIdOrderByScrapedOnDesc("testImdbId"))
                .thenReturn(Collections.singletonList(reviewOld));

        ImdbReview review = new ImdbReview(LocalDateTime.now(), "testImdbId", 2, "testTitle2",
                "testUsername2", LocalDate.of(2012, 2, 2),"testReview2");
        when(imdbReviewMapper.mapToImdbReviewList(any(), eq("testImdbId"))).thenReturn(Collections.singletonList(review));

        // When
        imdbService.prepareCachedImdbReviewsFor("testImdbId");

        // Then
        verify(imdbReviewRepository, times(1)).findByImdbIdOrderByScrapedOnDesc("testImdbId");
        verify(imdbReviewRepository, times(1)).deleteAllByImdbId("testImdbId");
        verify(imdbReviewScraper, times(1)).scrapeImdbReviews("testImdbId");
        verify(imdbReviewRepository, times(1)).save(any(ImdbReview.class));
    }

}