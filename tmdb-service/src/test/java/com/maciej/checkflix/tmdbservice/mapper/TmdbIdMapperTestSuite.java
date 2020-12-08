package com.maciej.checkflix.tmdbservice.mapper;

import com.maciej.checkflix.tmdbservice.client.util.Type;
import com.maciej.checkflix.tmdbservice.domain.idsearch.IdMovieResultDto;
import com.maciej.checkflix.tmdbservice.domain.idsearch.IdSearchResultsDto;
import com.maciej.checkflix.tmdbservice.domain.idsearch.IdTvResultDto;
import com.maciej.checkflix.tmdbservice.domain.idsearch.TmdbIdMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TmdbIdMapperTestSuite {

    @Autowired
    private TmdbIdMapper tmdbIdMapper;

    @Test
    public void mapToTmdbIdMapIfMovie() {
        // Given
        IdSearchResultsDto idSearchResultsDto = new IdSearchResultsDto(
                Collections.singletonList(
                        new IdMovieResultDto("testOverviewMovie", "testReleaseDateMovie", 1, true,
                                "testTitleMovie", Arrays.asList(1, 2), "testOriginalLanguageMovie",
                                "testOriginalTitleMovie", false)
                ),
                new ArrayList<>());
        String imdbId = "testImdbId";

        // When
        TmdbIdMap result = tmdbIdMapper.mapToTmdbIdMap(idSearchResultsDto, imdbId);

        // Then
        Assert.assertEquals("testImdbId", result.getImdbId());
        Assert.assertEquals(java.util.Optional.of(1), result.getTmdbId());
        Assert.assertEquals("testTitleMovie", result.getTitle());
        Assert.assertEquals(Type.MOVIE, result.getType());

    }

    @Test
    public void mapToTmdbIdMapIfTv() {
        // Given
        IdSearchResultsDto idSearchResultsDto = new IdSearchResultsDto(
                new ArrayList<>(),
                Collections.singletonList(
                        new IdTvResultDto(Arrays.asList("PL", "DE"), 2, "testOverviewTv", "testNameTv",
                                "testFirstAirDateTv", Arrays.asList(3, 4), "testOriginalLanguageTv",
                                "testOriginalNameTv")));
        String imdbId = "testImdbId";

        // When
        TmdbIdMap result = tmdbIdMapper.mapToTmdbIdMap(idSearchResultsDto, imdbId);

        // Then
        Assert.assertEquals("testImdbId", result.getImdbId());
        Assert.assertEquals(java.util.Optional.of(2), result.getTmdbId());
        Assert.assertEquals("testNameTv", result.getTitle());
        Assert.assertEquals(Type.SERIES, result.getType());

    }
}