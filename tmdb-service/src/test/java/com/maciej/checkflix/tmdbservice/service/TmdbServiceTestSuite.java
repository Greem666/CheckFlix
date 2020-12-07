package com.maciej.checkflix.tmdbservice.service;

import com.maciej.checkflix.tmdbservice.client.TmdbClient;
import com.maciej.checkflix.tmdbservice.client.util.Type;
import com.maciej.checkflix.tmdbservice.domain.idsearch.TmdbIdMap;
import com.maciej.checkflix.tmdbservice.domain.providersearch.CountryResultDto;
import com.maciej.checkflix.tmdbservice.domain.providersearch.ProviderDetailsDto;
import com.maciej.checkflix.tmdbservice.domain.providersearch.SearchResultsDto;
import com.maciej.checkflix.tmdbservice.domain.reviews.AuthorDetailsDto;
import com.maciej.checkflix.tmdbservice.domain.reviews.ReviewResultDto;
import com.maciej.checkflix.tmdbservice.repository.TmdbIdMapRepository;
import com.maciej.checkflix.tmdbservice.service.util.CountrySpecificProviderFactory;
import com.maciej.checkflix.tmdbservice.service.util.SupportedCountries;
import com.maciej.checkflix.tmdbservice.service.util.TmdbIdMapUpdater;
import com.maciej.checkflix.tmdbservice.service.util.adapter.TmdbIdDtoAdapter;
import com.maciej.checkflix.tmdbservice.service.util.adapter.TmdbIdTypeDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TmdbServiceTestSuite {

    @InjectMocks
    private TmdbService tmdbService;

    @Mock
    private TmdbClient tmdbClient;

    @Mock
    private TmdbIdDtoAdapter tmdbIdDtoAdapter;

    @Mock
    private TmdbIdMapRepository tmdbIdMapRepository;

    @Mock
    private TmdbIdMapUpdater tmdbIdMapUpdater;

    @Mock
    private CountrySpecificProviderFactory countrySpecificProviderFactory;

    @Test
    public void getProvidersForNoProvidersResult() {
        // Given
        TmdbIdTypeDto tmdbIdTypeDto = new TmdbIdTypeDto(1, Type.MOVIE);
        CountryResultDto countryResultDto = new CountryResultDto(
                "testLink", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        SearchResultsDto searchResultsDto = new SearchResultsDto(
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, countryResultDto, null, null, null, null,
                null, null, null, null, null, null);

        when(tmdbIdDtoAdapter.getMovieInfo()).thenReturn(tmdbIdTypeDto);
        when(tmdbClient.getProviders(tmdbIdTypeDto.getTmdbId(), tmdbIdTypeDto.getType())).thenReturn(searchResultsDto);
        when(countrySpecificProviderFactory.findLocalProvider(eq(searchResultsDto), eq("Poland"))).thenReturn(countryResultDto);

        // When
        CountryResultDto results = tmdbService.getProvidersFor("testImdbId", "Poland");

        // Then
        Assert.assertEquals("testLink", results.getLink());
        Assert.assertEquals(0, results.getFlatrate().size());
        Assert.assertEquals(0, results.getRent().size());
        Assert.assertEquals(0, results.getBuy().size());

        verify(tmdbClient, times(1)).getProviders(tmdbIdTypeDto.getTmdbId(), tmdbIdTypeDto.getType());
        verify(tmdbIdDtoAdapter, times(1)).getMovieInfo();
        verify(countrySpecificProviderFactory, times(1)).findLocalProvider(any(SearchResultsDto.class), anyString());
    }

    @Test
    public void getProvidersFor() {
        // Given
        TmdbIdTypeDto tmdbIdTypeDto = new TmdbIdTypeDto(1, Type.MOVIE);
        CountryResultDto countryResultDto = new CountryResultDto(
                "testLink",
                Collections.singletonList(
                        new ProviderDetailsDto(1, "/testLogoPath1",
                                1, "testProviderName1")),
                Collections.singletonList(
                        new ProviderDetailsDto(2, "/testLogoPath2",
                                2, "testProviderName2")),
                Collections.singletonList(
                        new ProviderDetailsDto(3, "/testLogoPath3",
                                3, "testProviderName3")));
        SearchResultsDto searchResultsDto = new SearchResultsDto(
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, countryResultDto, null, null, null, null,
                null, null, null, null, null, null);

        when(tmdbIdDtoAdapter.getMovieInfo()).thenReturn(tmdbIdTypeDto);
        when(tmdbClient.getProviders(tmdbIdTypeDto.getTmdbId(), tmdbIdTypeDto.getType())).thenReturn(searchResultsDto);
        when(countrySpecificProviderFactory.findLocalProvider(eq(searchResultsDto), eq("Poland"))).thenReturn(countryResultDto);

        // When
        CountryResultDto results = tmdbService.getProvidersFor("testImdbId", "Poland");

        // Then
        Assert.assertEquals("testLink", results.getLink());
        Assert.assertEquals(1, results.getFlatrate().size());
        Assert.assertEquals(1, results.getFlatrate().get(0).getDisplayPriority());
        Assert.assertEquals("https://image.tmdb.org/t/p/w500/testLogoPath1", results.getFlatrate().get(0).getLogoPath());
        Assert.assertEquals(1, results.getFlatrate().get(0).getProviderId());
        Assert.assertEquals("testProviderName1", results.getFlatrate().get(0).getProviderName());
        Assert.assertEquals(1, results.getRent().size());
        Assert.assertEquals(2, results.getRent().get(0).getDisplayPriority());
        Assert.assertEquals("https://image.tmdb.org/t/p/w500/testLogoPath2", results.getRent().get(0).getLogoPath());
        Assert.assertEquals(2, results.getRent().get(0).getProviderId());
        Assert.assertEquals("testProviderName2", results.getRent().get(0).getProviderName());
        Assert.assertEquals(1, results.getBuy().size());
        Assert.assertEquals(3, results.getBuy().get(0).getDisplayPriority());
        Assert.assertEquals("https://image.tmdb.org/t/p/w500/testLogoPath3", results.getBuy().get(0).getLogoPath());
        Assert.assertEquals(3, results.getBuy().get(0).getProviderId());
        Assert.assertEquals("testProviderName3", results.getBuy().get(0).getProviderName());

        verify(tmdbClient, times(1)).getProviders(tmdbIdTypeDto.getTmdbId(), tmdbIdTypeDto.getType());
        verify(tmdbIdDtoAdapter, times(1)).getMovieInfo();
        verify(countrySpecificProviderFactory, times(1)).findLocalProvider(any(SearchResultsDto.class), anyString());
    }

    @Test
    public void getSupportedCountries() {
        // Given
        List<String> requiredValues = Stream.of(SupportedCountries.values())
                .map(SupportedCountries::getCountryName)
                .collect(Collectors.toList());

        // When
        List<String> obtainedValues = tmdbService.getSupportedCountries();

        // Then
        Assert.assertEquals(requiredValues.size(), obtainedValues.size());
        obtainedValues.forEach(s -> {
            Assert.assertTrue(requiredValues.contains(s));
        });
    }

    @Test
    public void getReviewsFor() {
        // Given
        TmdbIdTypeDto tmdbIdTypeDto = new TmdbIdTypeDto(1, Type.MOVIE);
        AuthorDetailsDto authorDetailsDto = new AuthorDetailsDto(
                "testName", "testUsername", "testAvatarPath", 5.0);
        ReviewResultDto reviewDto = new ReviewResultDto(
                "testAuthor", authorDetailsDto, "testContent",
                "testCreatedAt", "testId", "testUpdatedAt", "testUrl");

        when(tmdbIdDtoAdapter.getMovieInfo()).thenReturn(tmdbIdTypeDto);
        when(tmdbClient.getReviews(tmdbIdTypeDto.getTmdbId(), tmdbIdTypeDto.getType()))
                .thenReturn(Collections.singletonList(reviewDto));

        // When
        List<ReviewResultDto> obtainedReviews = tmdbService.getReviewsFor("testImdbId");

        // Then
        Assert.assertEquals(1, obtainedReviews.size());
        Assert.assertEquals("testAuthor", obtainedReviews.get(0).getAuthor());
        Assert.assertEquals("testName", obtainedReviews.get(0).getAuthorDetails().getName());
        Assert.assertEquals("testUsername", obtainedReviews.get(0).getAuthorDetails().getUsername());
        Assert.assertEquals("testAvatarPath", obtainedReviews.get(0).getAuthorDetails().getAvatarPath());
        Assert.assertEquals(5.0, obtainedReviews.get(0).getAuthorDetails().getRating(), 0);
        Assert.assertEquals("testContent", obtainedReviews.get(0).getContent());
        Assert.assertEquals("testCreatedAt", obtainedReviews.get(0).getCreatedAt());
        Assert.assertEquals("testId", obtainedReviews.get(0).getId());
        Assert.assertEquals("testUpdatedAt", obtainedReviews.get(0).getUpdatedAt());
        Assert.assertEquals("testUrl", obtainedReviews.get(0).getUrl());

        verify(tmdbClient, times(1)).getReviews(tmdbIdTypeDto.getTmdbId(), tmdbIdTypeDto.getType());
        verify(tmdbIdDtoAdapter, times(1)).getMovieInfo();
    }

    @Test
    public void getReviewsForNoResults() {
        // Given
        TmdbIdTypeDto tmdbIdTypeDto = new TmdbIdTypeDto(1, Type.MOVIE);
        AuthorDetailsDto authorDetailsDto = new AuthorDetailsDto(
                "testName", "testUsername", "testAvatarPath", 5.0);
        ReviewResultDto reviewDto = new ReviewResultDto(
                "testAuthor", authorDetailsDto, "testContent",
                "testCreatedAt", "testId", "testUpdatedAt", "testUrl");

        when(tmdbIdDtoAdapter.getMovieInfo()).thenReturn(tmdbIdTypeDto);
        when(tmdbClient.getReviews(tmdbIdTypeDto.getTmdbId(), tmdbIdTypeDto.getType()))
                .thenReturn(null);

        // When
        List<ReviewResultDto> obtainedReviews = tmdbService.getReviewsFor("testImdbId");

        // Then
        Assert.assertEquals(0, obtainedReviews.size());

        verify(tmdbClient, times(1)).getReviews(tmdbIdTypeDto.getTmdbId(), tmdbIdTypeDto.getType());
        verify(tmdbIdDtoAdapter, times(1)).getMovieInfo();
    }

    @Test
    public void findNameByWithPreviousMapping() {
        // Given
        TmdbIdMap tmdbIdMap = new TmdbIdMap("testImdbId", 1, "testTitle", Type.MOVIE);
        when(tmdbIdMapRepository.findByImdbIdEquals(anyString())).thenReturn(tmdbIdMap);

        // When
        String result = tmdbService.findNameBy("testImdbId");

        // Then
        Assert.assertEquals("testTitle", result);

        verify(tmdbIdMapRepository, times(1)).findByImdbIdEquals(anyString());
    }

    @Test
    public void findNameByNoPreviousMapping() {
        // Given
        TmdbIdMap tmdbIdMap = new TmdbIdMap("testImdbId", 1, "testTitle", Type.MOVIE);
        when(tmdbIdMapRepository.findByImdbIdEquals(anyString())).thenReturn(null).thenReturn(tmdbIdMap);

        // When
        String result = tmdbService.findNameBy("testImdbId");

        // Then
        Assert.assertEquals("testTitle", result);

        verify(tmdbIdMapRepository, times(2)).findByImdbIdEquals(anyString());
        verify(tmdbIdMapUpdater, times(1)).updateTmdbIdMappingFromExternalApiFor(anyString());
    }

    @Test
    public void findNameByNoPreviousMappingException() {
        // Given
        TmdbIdMap tmdbIdMap = new TmdbIdMap("testImdbId", 1, "testTitle", Type.MOVIE);
        when(tmdbIdMapRepository.findByImdbIdEquals(anyString())).thenReturn(null).thenThrow(new NullPointerException());

        // When
        String result = tmdbService.findNameBy("testImdbId");

        // Then
        Assert.assertEquals("No title found", result);

        verify(tmdbIdMapRepository, times(2)).findByImdbIdEquals(anyString());
        verify(tmdbIdMapUpdater, times(1)).updateTmdbIdMappingFromExternalApiFor(anyString());
    }
}