package com.maciej.checkflix.watchlist.mapper;

import com.maciej.checkflix.watchlist.domain.ProviderType;
import com.maciej.checkflix.watchlist.domain.ProviderWatchlist;
import com.maciej.checkflix.watchlist.domain.ProviderWatchlistDto;
import com.maciej.checkflix.watchlist.service.TmdbService;
import com.maciej.checkflix.watchlist.service.util.SupportedCountries;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProviderWatchlistMapperTestSuite {

    @InjectMocks
    private ProviderWatchlistMapper providerWatchlistMapper;

    @Mock
    private TmdbService tmdbService;

    @Test
    public void mapToProviderWatchlist() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);
        ProviderWatchlistDto providerWatchlistDto = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail", "testImdbId", "testName",
                "Poland", "flatrate");

        // When
        ProviderWatchlist result = providerWatchlistMapper.mapToProviderWatchlist(providerWatchlistDto);

        // Then
        Assert.assertEquals(providerWatchlist.getId(), result.getId());
        Assert.assertEquals(providerWatchlist.getUsername(), result.getUsername());
        Assert.assertEquals(providerWatchlist.getEmail(), result.getEmail());
        Assert.assertEquals(providerWatchlist.getImdbId(), result.getImdbId());
        Assert.assertEquals(providerWatchlist.getCountry(), result.getCountry());
        Assert.assertEquals(providerWatchlist.getProviderType(), result.getProviderType());
    }

    @Test
    public void mapToProviderWatchlistDto() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);
        ProviderWatchlistDto providerWatchlistDto = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail", "testImdbId", "testName",
                "Poland", "flatrate");

        when(tmdbService.findNameBy(anyString())).thenReturn("testName");

        // When
        ProviderWatchlistDto result = providerWatchlistMapper.mapToProviderWatchlistDto(providerWatchlist);

        // Then
        Assert.assertEquals(providerWatchlistDto.getId(), result.getId());
        Assert.assertEquals(providerWatchlistDto.getUsername(), result.getUsername());
        Assert.assertEquals(providerWatchlistDto.getEmail(), result.getEmail());
        Assert.assertEquals(providerWatchlistDto.getImdbId(), result.getImdbId());
        Assert.assertEquals(providerWatchlistDto.getMovieName(), result.getMovieName());
        Assert.assertEquals(providerWatchlistDto.getCountry(), result.getCountry());
        Assert.assertEquals(providerWatchlistDto.getProviderType(), result.getProviderType());

        verify(tmdbService, times(1)).findNameBy(anyString());
    }

    @Test
    public void mapToProviderWatchlistList() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);
        ProviderWatchlistDto providerWatchlistDto = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail", "testImdbId", "testName",
                "Poland", "flatrate");

        // When
        List<ProviderWatchlist> result = providerWatchlistMapper.mapToProviderWatchlistList(
                Collections.singletonList(providerWatchlistDto));

        // Then
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(providerWatchlist.getId(), result.get(0).getId());
        Assert.assertEquals(providerWatchlist.getUsername(), result.get(0).getUsername());
        Assert.assertEquals(providerWatchlist.getEmail(), result.get(0).getEmail());
        Assert.assertEquals(providerWatchlist.getImdbId(), result.get(0).getImdbId());
        Assert.assertEquals(providerWatchlist.getCountry(), result.get(0).getCountry());
        Assert.assertEquals(providerWatchlist.getProviderType(), result.get(0).getProviderType());
    }

    @Test
    public void mapToProviderWatchlistDtoList() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);
        ProviderWatchlistDto providerWatchlistDto = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail", "testImdbId", "testName",
                "Poland", "flatrate");

        when(tmdbService.findNameBy(anyString())).thenReturn("testName");

        // When
        List<ProviderWatchlistDto> result = providerWatchlistMapper.mapToProviderWatchlistDtoList(
                Collections.singletonList(providerWatchlist));

        // Then
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(providerWatchlistDto.getId(), result.get(0).getId());
        Assert.assertEquals(providerWatchlistDto.getUsername(), result.get(0).getUsername());
        Assert.assertEquals(providerWatchlistDto.getEmail(), result.get(0).getEmail());
        Assert.assertEquals(providerWatchlistDto.getImdbId(), result.get(0).getImdbId());
        Assert.assertEquals(providerWatchlistDto.getMovieName(), result.get(0).getMovieName());
        Assert.assertEquals(providerWatchlistDto.getCountry(), result.get(0).getCountry());
        Assert.assertEquals(providerWatchlistDto.getProviderType(), result.get(0).getProviderType());

        verify(tmdbService, times(1)).findNameBy(anyString());
    }
}