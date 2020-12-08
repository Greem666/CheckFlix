package com.maciej.checkflix.watchlist.service;

import com.maciej.checkflix.watchlist.client.TmdbClient;
import com.maciej.checkflix.watchlist.domain.*;
import com.maciej.checkflix.watchlist.mapper.ProviderWatchlistMapper;
import com.maciej.checkflix.watchlist.repository.ProviderWatchlistRepository;
import com.maciej.checkflix.watchlist.service.util.SupportedCountries;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProviderWatchlistService {
    private final ProviderWatchlistRepository providerWatchlistRepository;
    private final ProviderWatchlistMapper providerWatchlistMapper;
    private final TmdbClient tmdbClient;

    public List<ProviderWatchlistDto> getAllItemsOnWatchlist() {
        return providerWatchlistMapper.mapToProviderWatchlistDtoList(providerWatchlistRepository.findAll());
    }

    public ProviderWatchlistDto getItemOnWatchlist(Long itemId) {
        Optional<ProviderWatchlist> item = providerWatchlistRepository.findById(itemId);
        return providerWatchlistMapper.mapToProviderWatchlistDto(item.orElse(new ProviderWatchlist()));
    }

    public ProviderWatchlistDto addNewItemToWatchlist(ProviderWatchlistDto providerWatchlistDto) {
        Optional<ProviderWatchlist> previousEntry = providerWatchlistRepository.findByEmailAndImdbIdAndCountryAndProviderType(
                providerWatchlistDto.getEmail(),
                providerWatchlistDto.getImdbId(),
                SupportedCountries.from(providerWatchlistDto.getCountry()),
                ProviderType.from(providerWatchlistDto.getProviderType())
        );
        if (!previousEntry.isPresent()) {
            return providerWatchlistMapper.mapToProviderWatchlistDto(
                    providerWatchlistRepository.save(
                            providerWatchlistMapper.mapToProviderWatchlist(providerWatchlistDto)
                    )
            );
        }
        return new ProviderWatchlistDto();
    }

    public ProviderWatchlistDto modifyItemOnWatchlist(ProviderWatchlistDto providerWatchlistDto) {
        Optional<ProviderWatchlist> previousEntry = providerWatchlistRepository.findById(
                providerWatchlistDto.getId()
        );
        if (previousEntry.isPresent()) {
            return providerWatchlistMapper.mapToProviderWatchlistDto(
                    providerWatchlistRepository.save(
                            providerWatchlistMapper.mapToProviderWatchlist(providerWatchlistDto)
                    )
            );
        }
        return new ProviderWatchlistDto();
    }

    public void removeItemFromWatchlist(Long itemId) {
        Optional<ProviderWatchlist> previousEntry = providerWatchlistRepository.findById(itemId);
        if (previousEntry.isPresent()) {
            providerWatchlistRepository.deleteById(itemId);
        }
    }

    public List<ProviderWatchlistDto> getItemOnProviderWatchlistBy(String phrase) {
        List<ProviderWatchlist> searchResults = providerWatchlistRepository.findBySearchPhrase(phrase);
        return providerWatchlistMapper.mapToProviderWatchlistDtoList(searchResults);
    }

    public List<String> getProviderTypes() {
        return Arrays.stream(ProviderType.values()).map(ProviderType::getTmdbName).collect(Collectors.toList());
    }


    public List<ProviderDetailsDto> getProvidersFor(String imdbId, String countryName, String providerType) {
        CountryResultDto foundProviders = tmdbClient.getProvidersFor(imdbId, countryName);

        List<ProviderDetailsDto> provider;
        switch(providerType) {
            default:
            case "flatrate":
                provider = foundProviders.getFlatrate();
                break;
            case "buy":
                provider = foundProviders.getBuy();
                break;
            case "rent":
                provider = foundProviders.getRent();
                break;
        }
        return Optional.ofNullable(provider).orElse(new ArrayList<>());
    }
}
