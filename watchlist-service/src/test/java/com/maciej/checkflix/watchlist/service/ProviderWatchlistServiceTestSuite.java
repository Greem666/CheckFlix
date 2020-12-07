package com.maciej.checkflix.watchlist.service;

import com.maciej.checkflix.watchlist.client.TmdbClient;
import com.maciej.checkflix.watchlist.domain.*;
import com.maciej.checkflix.watchlist.mapper.ProviderWatchlistMapper;
import com.maciej.checkflix.watchlist.repository.ProviderWatchlistRepository;
import com.maciej.checkflix.watchlist.service.util.SupportedCountries;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProviderWatchlistServiceTestSuite {

    @InjectMocks
    private ProviderWatchlistService providerWatchlistService;

    @Mock
    private ProviderWatchlistRepository providerWatchlistRepository;

    @Mock
    private TmdbClient tmdbClient;

    @Mock
    private TmdbService tmdbService;

    @Mock
    private ProviderWatchlistMapper providerWatchlistMapper;

    @Test
    public void getAllItemsOnWatchlist() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);
        ProviderWatchlistDto providerWatchlistDto = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail", "testImdbId", "testName",
                "Poland", "flatrate");

        when(providerWatchlistRepository.findAll()).thenReturn(Collections.singletonList(providerWatchlist));
        when(providerWatchlistMapper.mapToProviderWatchlistDtoList(Collections.singletonList(providerWatchlist)))
                .thenReturn(Collections.singletonList(providerWatchlistDto));

        // When
        List<ProviderWatchlistDto> results = providerWatchlistService.getAllItemsOnWatchlist();

        // Then
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(providerWatchlist.getUsername(), results.get(0).getUsername());
        Assert.assertEquals(providerWatchlist.getEmail(), results.get(0).getEmail());
        Assert.assertEquals(providerWatchlist.getImdbId(), results.get(0).getImdbId());
        Assert.assertEquals(providerWatchlist.getProviderType().getTmdbName(), results.get(0).getProviderType());
        Assert.assertEquals("testName", results.get(0).getMovieName());
        Assert.assertEquals(SupportedCountries.POLAND.getCountryName(), results.get(0).getCountry());

        verify(providerWatchlistRepository, times(1)).findAll();
    }

    @Test
    public void getItemOnWatchlist() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);
        ProviderWatchlistDto providerWatchlistDto = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail", "testImdbId", "testName",
                "Poland", "flatrate");

        when(providerWatchlistRepository.findById(anyLong())).thenReturn(Optional.of(providerWatchlist));
        when(providerWatchlistMapper.mapToProviderWatchlistDto(providerWatchlist)).thenReturn(providerWatchlistDto);

        // When
        ProviderWatchlistDto results = providerWatchlistService.getItemOnWatchlist(anyLong());

        // Then
        Assert.assertEquals(providerWatchlist.getUsername(), results.getUsername());
        Assert.assertEquals(providerWatchlist.getEmail(), results.getEmail());
        Assert.assertEquals(providerWatchlist.getImdbId(), results.getImdbId());
        Assert.assertEquals(providerWatchlist.getProviderType().getTmdbName(), results.getProviderType());
        Assert.assertEquals("testName", results.getMovieName());
        Assert.assertEquals(SupportedCountries.POLAND.getCountryName(), results.getCountry());

        verify(providerWatchlistRepository, times(1)).findById(anyLong());
    }

    @Test
    public void addNewItemToWatchlistNoPreviousRecord() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);
        ProviderWatchlistDto providerWatchlistDto = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail", "testImdbId", "testName",
                "Poland", "flatrate");

        when(providerWatchlistRepository.findByEmailAndImdbIdAndCountryAndProviderType(
                anyString(), anyString(), any(SupportedCountries.class), any(ProviderType.class)))
                .thenReturn(Optional.ofNullable(null));

        when(providerWatchlistRepository.save(providerWatchlist)).thenReturn(providerWatchlist);

        when(providerWatchlistMapper.mapToProviderWatchlistDto(providerWatchlist)).thenReturn(providerWatchlistDto);
        when(providerWatchlistMapper.mapToProviderWatchlist(providerWatchlistDto)).thenReturn(providerWatchlist);

        // When
        ProviderWatchlistDto result = providerWatchlistService.addNewItemToWatchlist(providerWatchlistDto);

        // Then
        Assert.assertEquals(providerWatchlist.getUsername(), result.getUsername());
        Assert.assertEquals(providerWatchlist.getEmail(), result.getEmail());
        Assert.assertEquals(providerWatchlist.getImdbId(), result.getImdbId());
        Assert.assertEquals(providerWatchlist.getProviderType().getTmdbName(), result.getProviderType());
        Assert.assertEquals("testName", result.getMovieName());
        Assert.assertEquals(SupportedCountries.POLAND.getCountryName(), result.getCountry());

        verify(providerWatchlistRepository, times(1)).save(any(ProviderWatchlist.class));
    }

    @Test
    public void addNewItemToWatchlistWithPreviousRecord() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);
        ProviderWatchlistDto providerWatchlistDto = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail", "testImdbId", "testName",
                "Poland", "flatrate");

        when(providerWatchlistRepository.findByEmailAndImdbIdAndCountryAndProviderType(
                anyString(), anyString(), any(SupportedCountries.class), any(ProviderType.class)))
                .thenReturn(Optional.ofNullable(providerWatchlist));

        // When
        ProviderWatchlistDto result = providerWatchlistService.addNewItemToWatchlist(providerWatchlistDto);

        // Then
        assertNull(result.getUsername());
        assertNull(result.getEmail());
        assertNull(result.getImdbId());
        assertNull(result.getProviderType());
        assertNull(result.getMovieName());
        assertNull(result.getCountry());

        verify(providerWatchlistRepository, never()).save(any(ProviderWatchlist.class));
    }

    @Test
    public void modifyItemOnWatchlistNoPreviousRecord() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);
        ProviderWatchlistDto providerWatchlistDto = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail", "testImdbId", "testName",
                "Poland", "flatrate");

        when(providerWatchlistRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(null));

        // When
        ProviderWatchlistDto result = providerWatchlistService.modifyItemOnWatchlist(providerWatchlistDto);

        // Then
        assertNull(result.getUsername());
        assertNull(result.getEmail());
        assertNull(result.getImdbId());
        assertNull(result.getProviderType());
        assertNull(result.getMovieName());
        assertNull(result.getCountry());

        verify(providerWatchlistRepository, never()).save(any(ProviderWatchlist.class));
    }

    @Test
    public void modifyNewItemToWatchlistWithPreviousRecord() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);
        ProviderWatchlistDto providerWatchlistDto = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail", "testImdbId", "testName",
                "Poland", "flatrate");

        when(providerWatchlistRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(providerWatchlist));

        when(providerWatchlistRepository.save(providerWatchlist)).thenReturn(providerWatchlist);

        when(providerWatchlistMapper.mapToProviderWatchlistDto(providerWatchlist)).thenReturn(providerWatchlistDto);
        when(providerWatchlistMapper.mapToProviderWatchlist(providerWatchlistDto)).thenReturn(providerWatchlist);

        // When
        ProviderWatchlistDto result = providerWatchlistService.modifyItemOnWatchlist(providerWatchlistDto);

        // Then
        Assert.assertEquals(providerWatchlist.getUsername(), result.getUsername());
        Assert.assertEquals(providerWatchlist.getEmail(), result.getEmail());
        Assert.assertEquals(providerWatchlist.getImdbId(), result.getImdbId());
        Assert.assertEquals(providerWatchlist.getProviderType().getTmdbName(), result.getProviderType());
        Assert.assertEquals("testName", result.getMovieName());
        Assert.assertEquals(SupportedCountries.POLAND.getCountryName(), result.getCountry());

        verify(providerWatchlistRepository, times(1)).save(any(ProviderWatchlist.class));
    }

    @Test
    public void removeItemFromWatchlistItemInDb() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);

        when(providerWatchlistRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(providerWatchlist));

        // When
        providerWatchlistService.removeItemFromWatchlist(anyLong());

        // Then

        verify(providerWatchlistRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void removeItemFromWatchlistItemNotInDb() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);

        when(providerWatchlistRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(null));

        // When
        providerWatchlistService.removeItemFromWatchlist(anyLong());

        // Then

        verify(providerWatchlistRepository, never()).deleteById(anyLong());
    }

    @Test
    public void getItemOnProviderWatchlistByPresentItem() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);
        ProviderWatchlistDto providerWatchlistDto = new ProviderWatchlistDto(
                1L, "testUserName", "testEmail", "testImdbId", "testName",
                "Poland", "flatrate");

        when(providerWatchlistRepository.findBySearchPhrase(anyString()))
                .thenReturn(Collections.singletonList(providerWatchlist));
        when(providerWatchlistMapper.mapToProviderWatchlistDtoList(Collections.singletonList(providerWatchlist)))
                .thenReturn(Collections.singletonList(providerWatchlistDto));

        // When
        List<ProviderWatchlistDto> results = providerWatchlistService.getItemOnProviderWatchlistBy(anyString());

        // Then
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(providerWatchlist.getUsername(), results.get(0).getUsername());
        Assert.assertEquals(providerWatchlist.getEmail(), results.get(0).getEmail());
        Assert.assertEquals(providerWatchlist.getImdbId(), results.get(0).getImdbId());
        Assert.assertEquals(providerWatchlist.getProviderType().getTmdbName(), results.get(0).getProviderType());
        Assert.assertEquals("testName", results.get(0).getMovieName());
        Assert.assertEquals(SupportedCountries.POLAND.getCountryName(), results.get(0).getCountry());

        verify(providerWatchlistRepository, times(1)).findBySearchPhrase(anyString());
    }

    @Test
    public void getItemOnProviderWatchlistByAbsentItem() {
        // Given
        ProviderWatchlist providerWatchlist = new ProviderWatchlist(
                1L, "testUserName", "testEmail", "testImdbId",
                SupportedCountries.POLAND, ProviderType.STREAMING);

        when(providerWatchlistRepository.findBySearchPhrase(anyString()))
                .thenReturn(new ArrayList<>());
        when(providerWatchlistMapper.mapToProviderWatchlistDtoList(any()))
                .thenReturn(new ArrayList<>());

        // When
        List<ProviderWatchlistDto> results = providerWatchlistService.getItemOnProviderWatchlistBy(anyString());

        // Then
        Assert.assertEquals(0, results.size());

        verify(providerWatchlistRepository, times(1)).findBySearchPhrase(anyString());
    }

    @Test
    public void getProviderTypes() {
        // Given
        List<String> requiredValues = Arrays.stream(ProviderType.values())
                .map(ProviderType::getTmdbName)
                .collect(Collectors.toList());

        // When
        List<String> results = providerWatchlistService.getProviderTypes();

        // Then
        Assert.assertEquals(requiredValues.size(), results.size());
        results.forEach(s -> {
            Assert.assertTrue(requiredValues.contains(s));
        });
    }

    @Test
    public void getProvidersForFlatrate() {
        // Given
        CountryResultDto countryResultDto = new CountryResultDto(
                "testLink",
                Collections.singletonList(
                        new ProviderDetailsDto(1, "testLogoPath", 1, "testProviderName")),
                new ArrayList<>(), new ArrayList<>());

        when(tmdbClient.getProvidersFor("testImdbId", "testCountry")).thenReturn(countryResultDto);

        // When
        List<ProviderDetailsDto> providers = providerWatchlistService.getProvidersFor(
                "testImdbId", "testCountry", "flatrate");

        // Then
        Assert.assertEquals(1, providers.size());
        Assert.assertEquals(1, providers.get(0).getDisplayPriority());
        Assert.assertEquals("testLogoPath", providers.get(0).getLogoPath());
        Assert.assertEquals(1, providers.get(0).getProviderId());
        Assert.assertEquals("testProviderName", providers.get(0).getProviderName());

        verify(tmdbClient, times(1)).getProvidersFor(anyString(), anyString());
    }

    @Test
    public void getProvidersForRent() {
        // Given
        CountryResultDto countryResultDto = new CountryResultDto(
                "testLink",
                new ArrayList<>(),
                Collections.singletonList(
                        new ProviderDetailsDto(2, "testLogoPath", 2, "testProviderName")),
                new ArrayList<>()
                );

        when(tmdbClient.getProvidersFor("testImdbId", "testCountry")).thenReturn(countryResultDto);

        // When
        List<ProviderDetailsDto> providers = providerWatchlistService.getProvidersFor(
                "testImdbId", "testCountry", "rent");

        // Then
        Assert.assertEquals(1, providers.size());
        Assert.assertEquals(2, providers.get(0).getDisplayPriority());
        Assert.assertEquals("testLogoPath", providers.get(0).getLogoPath());
        Assert.assertEquals(2, providers.get(0).getProviderId());
        Assert.assertEquals("testProviderName", providers.get(0).getProviderName());

        verify(tmdbClient, times(1)).getProvidersFor(anyString(), anyString());
    }

    @Test
    public void getProvidersForBuy() {
        // Given
        CountryResultDto countryResultDto = new CountryResultDto(
                "testLink",
                new ArrayList<>(),
                new ArrayList<>(),
                Collections.singletonList(
                        new ProviderDetailsDto(3, "testLogoPath", 3, "testProviderName"))
                );

        when(tmdbClient.getProvidersFor("testImdbId", "testCountry")).thenReturn(countryResultDto);

        // When
        List<ProviderDetailsDto> providers = providerWatchlistService.getProvidersFor(
                "testImdbId", "testCountry", "buy");

        // Then
        Assert.assertEquals(1, providers.size());
        Assert.assertEquals(3, providers.get(0).getDisplayPriority());
        Assert.assertEquals("testLogoPath", providers.get(0).getLogoPath());
        Assert.assertEquals(3, providers.get(0).getProviderId());
        Assert.assertEquals("testProviderName", providers.get(0).getProviderName());

        verify(tmdbClient, times(1)).getProvidersFor(anyString(), anyString());
    }
}