package com.maciej.checkflix.tmdbservice.client;

import com.maciej.checkflix.tmdbservice.client.util.Type;
import com.maciej.checkflix.tmdbservice.config.TmdbConfig;
import com.maciej.checkflix.tmdbservice.domain.idsearch.IdSearchResultsDto;
import com.maciej.checkflix.tmdbservice.domain.providersearch.CountryResultDto;
import com.maciej.checkflix.tmdbservice.domain.providersearch.ProviderSearchOverviewDto;
import com.maciej.checkflix.tmdbservice.domain.providersearch.SearchResultsDto;
import com.maciej.checkflix.tmdbservice.domain.reviews.ReviewResultDto;
import com.maciej.checkflix.tmdbservice.domain.reviews.ReviewsDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TmdbClientTestSuite {

    @InjectMocks
    private TmdbClient tmdbClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TmdbConfig tmdbConfig;

    private CountryResultDto countryResultDto;
    private SearchResultsDto searchResultsDto;
    private ProviderSearchOverviewDto providerSearchOverviewDto;

    @Before
    public void setUp() {
        countryResultDto = new CountryResultDto(
                "testLink", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        searchResultsDto = new SearchResultsDto(
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, countryResultDto, null, null, null, null,
                null, null, null, null, null, null);
        providerSearchOverviewDto = new ProviderSearchOverviewDto(1L, searchResultsDto);
    }

    @Test
    public void getProvidersForMovie() {
        // Given
        int tmdbId = 123;

        when(tmdbConfig.getApiUrl()).thenReturn("http://test.com");
        when(tmdbConfig.getApiKey()).thenReturn("secret-key");

        URI testUri = URI.create("http://test.com/movie/" + tmdbId + "/watch/providers?api_key=secret-key");

        when(restTemplate.getForObject(
                testUri,
                ProviderSearchOverviewDto.class))
                .thenReturn(providerSearchOverviewDto);

        // When
        SearchResultsDto result = tmdbClient.getProviders(tmdbId, Type.MOVIE);

        // Then
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getPl());
        Assert.assertEquals("testLink", result.getPl().getLink());

        verify(restTemplate, times(1)).getForObject(testUri, ProviderSearchOverviewDto.class);
    }

    @Test
    public void getProvidersForMovieNoneFound() {
        // Given
        int tmdbId = 123;

        when(tmdbConfig.getApiUrl()).thenReturn("http://test.com");
        when(tmdbConfig.getApiKey()).thenReturn("secret-key");

        URI testUri = URI.create("http://test.com/movie/" + tmdbId + "/watch/providers?api_key=secret-key");

        when(restTemplate.getForObject(
                testUri,
                ProviderSearchOverviewDto.class))
                .thenReturn(null);

        // When
        SearchResultsDto result = tmdbClient.getProviders(tmdbId, Type.MOVIE);

        // Then
        Assert.assertNotNull(result);
        Assert.assertNull(result.getPl());

        verify(restTemplate, times(1)).getForObject(testUri, ProviderSearchOverviewDto.class);
    }

    @Test
    public void getProvidersForTv() {
        // Given
        int tmdbId = 123;

        when(tmdbConfig.getApiUrl()).thenReturn("http://test.com");
        when(tmdbConfig.getApiKey()).thenReturn("secret-key");

        URI testUri = URI.create("http://test.com/tv/" + tmdbId + "/watch/providers?api_key=secret-key");

        when(restTemplate.getForObject(
                testUri,
                ProviderSearchOverviewDto.class))
                .thenReturn(providerSearchOverviewDto);

        // When
        SearchResultsDto result = tmdbClient.getProviders(tmdbId, Type.SERIES);

        // Then
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getPl());
        Assert.assertEquals("testLink", result.getPl().getLink());

        verify(restTemplate, times(1)).getForObject(testUri, ProviderSearchOverviewDto.class);
    }

    @Test
    public void getProvidersForTvNoneFound() {
        // Given
        int tmdbId = 123;

        when(tmdbConfig.getApiUrl()).thenReturn("http://test.com");
        when(tmdbConfig.getApiKey()).thenReturn("secret-key");

        URI testUri = URI.create("http://test.com/tv/" + tmdbId + "/watch/providers?api_key=secret-key");

        when(restTemplate.getForObject(
                testUri,
                ProviderSearchOverviewDto.class))
                .thenReturn(null);

        // When
        SearchResultsDto result = tmdbClient.getProviders(tmdbId, Type.SERIES);

        // Then
        Assert.assertNotNull(result);
        Assert.assertNull(result.getPl());

        verify(restTemplate, times(1)).getForObject(testUri, ProviderSearchOverviewDto.class);
    }

    @Test
    public void getMovieOrTvTmdbId() {
        // Given
        String imdbId = "testImdbId";
        IdSearchResultsDto idSearchResultsDto = new IdSearchResultsDto(new ArrayList<>(), new ArrayList<>());

        when(tmdbConfig.getApiUrl()).thenReturn("http://test.com");
        when(tmdbConfig.getApiKey()).thenReturn("secret-key");

        URI testUri = URI.create("http://test.com/find/" + imdbId + "?api_key=secret-key&external_source=imdb_id");

        when(restTemplate.getForObject(
                testUri,
                IdSearchResultsDto.class))
                .thenReturn(idSearchResultsDto);

        // When
        IdSearchResultsDto result = tmdbClient.getMovieOrTvTmdbId(imdbId);

        // Then
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getMovieResults());
        Assert.assertNotNull(result.getTvResults());

        verify(restTemplate, times(1)).getForObject(testUri, IdSearchResultsDto.class);
    }

    @Test
    public void getReviewsForMovie() {
        // Given
        int tmdbId = 123;

        ReviewResultDto reviewResultDto = new ReviewResultDto(
                "testAuthor", null, "testContent", "testCreatedAt",
                "testId", "testUpdatedAt", "testUrl");
        ReviewsDto reviewsDto = new ReviewsDto(1L, 2L, Collections.singletonList(reviewResultDto), 3L, 4L);

        when(tmdbConfig.getApiUrl()).thenReturn("http://test.com");
        when(tmdbConfig.getApiKey()).thenReturn("secret-key");

        URI testUri = URI.create("http://test.com/movie/" + tmdbId + "/reviews?api_key=secret-key");

        when(restTemplate.getForObject(
                testUri,
                ReviewsDto.class))
                .thenReturn(reviewsDto);

        // When
        List<ReviewResultDto> results = tmdbClient.getReviews(tmdbId, Type.MOVIE);

        // Then
        Assert.assertEquals(1, results.size());
        Assert.assertEquals("testAuthor", results.get(0).getAuthor());
        assertNull(results.get(0).getAuthorDetails());
        Assert.assertEquals("testContent", results.get(0).getContent());
        Assert.assertEquals("testCreatedAt", results.get(0).getCreatedAt());
        Assert.assertEquals("testId", results.get(0).getId());
        Assert.assertEquals("testUpdatedAt", results.get(0).getUpdatedAt());
        Assert.assertEquals("testUrl", results.get(0).getUrl());

        verify(restTemplate, times(1)).getForObject(testUri, ReviewsDto.class);
    }

    @Test
    public void getReviewsForMovieNoneFound() {
        // Given
        int tmdbId = 123;

        ReviewResultDto reviewResultDto = new ReviewResultDto(
                "testAuthor", null, "testContent", "testCreatedAt",
                "testId", "testUpdatedAt", "testUrl");
        ReviewsDto reviewsDto = new ReviewsDto(1L, 2L, Collections.singletonList(reviewResultDto), 3L, 4L);

        when(tmdbConfig.getApiUrl()).thenReturn("http://test.com");
        when(tmdbConfig.getApiKey()).thenReturn("secret-key");

        URI testUri = URI.create("http://test.com/movie/" + tmdbId + "/reviews?api_key=secret-key");

        when(restTemplate.getForObject(
                testUri,
                ReviewsDto.class))
                .thenReturn(null);

        // When
        List<ReviewResultDto> results = tmdbClient.getReviews(tmdbId, Type.MOVIE);

        // Then
        Assert.assertEquals(0, results.size());

        verify(restTemplate, times(1)).getForObject(testUri, ReviewsDto.class);
    }

    @Test
    public void getReviewsForTv() {
        // Given
        int tmdbId = 123;

        ReviewResultDto reviewResultDto = new ReviewResultDto(
                "testAuthor", null, "testContent", "testCreatedAt",
                "testId", "testUpdatedAt", "testUrl");
        ReviewsDto reviewsDto = new ReviewsDto(1L, 2L, Collections.singletonList(reviewResultDto), 3L, 4L);

        when(tmdbConfig.getApiUrl()).thenReturn("http://test.com");
        when(tmdbConfig.getApiKey()).thenReturn("secret-key");

        URI testUri = URI.create("http://test.com/tv/" + tmdbId + "/reviews?api_key=secret-key");

        when(restTemplate.getForObject(
                testUri,
                ReviewsDto.class))
                .thenReturn(reviewsDto);

        // When
        List<ReviewResultDto> results = tmdbClient.getReviews(tmdbId, Type.SERIES);

        // Then
        Assert.assertEquals(1, results.size());
        Assert.assertEquals("testAuthor", results.get(0).getAuthor());
        assertNull(results.get(0).getAuthorDetails());
        Assert.assertEquals("testContent", results.get(0).getContent());
        Assert.assertEquals("testCreatedAt", results.get(0).getCreatedAt());
        Assert.assertEquals("testId", results.get(0).getId());
        Assert.assertEquals("testUpdatedAt", results.get(0).getUpdatedAt());
        Assert.assertEquals("testUrl", results.get(0).getUrl());

        verify(restTemplate, times(1)).getForObject(testUri, ReviewsDto.class);
    }

    @Test
    public void getReviewsForTvNoneFound() {
        // Given
        int tmdbId = 123;

        ReviewResultDto reviewResultDto = new ReviewResultDto(
                "testAuthor", null, "testContent", "testCreatedAt",
                "testId", "testUpdatedAt", "testUrl");
        ReviewsDto reviewsDto = new ReviewsDto(1L, 2L, Collections.singletonList(reviewResultDto), 3L, 4L);

        when(tmdbConfig.getApiUrl()).thenReturn("http://test.com");
        when(tmdbConfig.getApiKey()).thenReturn("secret-key");

        URI testUri = URI.create("http://test.com/tv/" + tmdbId + "/reviews?api_key=secret-key");

        when(restTemplate.getForObject(
                testUri,
                ReviewsDto.class))
                .thenReturn(null);

        // When
        List<ReviewResultDto> results = tmdbClient.getReviews(tmdbId, Type.SERIES);

        // Then
        Assert.assertEquals(0, results.size());

        verify(restTemplate, times(1)).getForObject(testUri, ReviewsDto.class);
    }
}