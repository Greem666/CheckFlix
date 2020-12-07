package com.maciej.checkflix.webscraper.mapper;

import com.maciej.checkflix.webscraper.domain.ImdbReview;
import com.maciej.checkflix.webscraper.domain.ImdbReviewDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImdbReviewMapperTestSuite {

    @Autowired
    private ImdbReviewMapper imdbReviewMapper;

    @Test
    public void mapToImdbReview() {
        // Given
        ImdbReviewDto reviewDto = new ImdbReviewDto(2, "testTitle", "testUsername",
                "1 January 2011", "testReview");

        // When
        ImdbReview reviewMapped = imdbReviewMapper.mapToImdbReview(reviewDto, "testImdbId");

        // Then
        Assert.assertEquals("testImdbId", reviewMapped.getImdbId());
        Assert.assertEquals(2, reviewMapped.getRating());
        Assert.assertEquals("testTitle", reviewMapped.getTitle());
        Assert.assertEquals("testUsername", reviewMapped.getUsername());
        Assert.assertEquals(LocalDate.of(2011, 1, 1), reviewMapped.getDate());
        Assert.assertEquals("testReview", reviewMapped.getReview());
    }

    @Test
    public void mapToImdbReviewDto() {
        // Given
        ImdbReview review = new ImdbReview(LocalDateTime.now(), "testImdbId", 2, "testTitle",
                "testUsername", LocalDate.of(2011, 1, 1),"testReview");

        // When
        ImdbReviewDto reviewDtoMapped = imdbReviewMapper.mapToImdbReviewDto(review);

        // Then
        Assert.assertEquals(2, reviewDtoMapped.getRating());
        Assert.assertEquals("testTitle", reviewDtoMapped.getTitle());
        Assert.assertEquals("testUsername", reviewDtoMapped.getUsername());
        Assert.assertEquals("2011-01-01", reviewDtoMapped.getDate());
        Assert.assertEquals("testReview", reviewDtoMapped.getReview());
    }

    @Test
    public void mapToImdbReviewList() {
        // Given
        ImdbReviewDto reviewDto = new ImdbReviewDto(2, "testTitle", "testUsername",
                "1 January 2011", "testReview");

        // When
        List<ImdbReview> reviewMappedList = imdbReviewMapper.mapToImdbReviewList(
                Collections.singletonList(reviewDto), "testImdbId");

        // Then
        Assert.assertEquals(1, reviewMappedList.size());
        Assert.assertEquals("testImdbId", reviewMappedList.get(0).getImdbId());
        Assert.assertEquals(2, reviewMappedList.get(0).getRating());
        Assert.assertEquals("testTitle", reviewMappedList.get(0).getTitle());
        Assert.assertEquals("testUsername", reviewMappedList.get(0).getUsername());
        Assert.assertEquals(LocalDate.of(2011, 1, 1), reviewMappedList.get(0).getDate());
        Assert.assertEquals("testReview", reviewMappedList.get(0).getReview());
    }

    @Test
    public void mapToImdbReviewDtoList() {
        // Given
        ImdbReview review = new ImdbReview(LocalDateTime.now(), "testImdbId", 2, "testTitle",
                "testUsername", LocalDate.of(2011, 1, 1),"testReview");

        // When
        List<ImdbReviewDto> reviewDtoMappedList = imdbReviewMapper.mapToImdbReviewDtoList(Collections.singletonList(review));

        // Then
        Assert.assertEquals(1, reviewDtoMappedList.size());
        Assert.assertEquals(2, reviewDtoMappedList.get(0).getRating());
        Assert.assertEquals("testTitle", reviewDtoMappedList.get(0).getTitle());
        Assert.assertEquals("testUsername", reviewDtoMappedList.get(0).getUsername());
        Assert.assertEquals("2011-01-01", reviewDtoMappedList.get(0).getDate());
        Assert.assertEquals("testReview", reviewDtoMappedList.get(0).getReview());
    }
}