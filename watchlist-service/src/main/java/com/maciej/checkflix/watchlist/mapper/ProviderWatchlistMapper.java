package com.maciej.checkflix.watchlist.mapper;

import com.maciej.checkflix.watchlist.domain.ProviderType;
import com.maciej.checkflix.watchlist.domain.ProviderWatchlist;
import com.maciej.checkflix.watchlist.domain.ProviderWatchlistDto;
import com.maciej.checkflix.watchlist.service.TmdbService;
import com.maciej.checkflix.watchlist.service.util.SupportedCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProviderWatchlistMapper {

    @Autowired
    private TmdbService tmdbService;

    public ProviderWatchlist mapToProviderWatchlist(ProviderWatchlistDto providerWatchlistDto) {
        return new ProviderWatchlist(
                providerWatchlistDto.getId(),
                providerWatchlistDto.getUsername(),
                providerWatchlistDto.getEmail(),
                providerWatchlistDto.getImdbId(),
                SupportedCountries.from(providerWatchlistDto.getCountry()),
                ProviderType.from(providerWatchlistDto.getProviderType())
        );
    }

    public ProviderWatchlistDto mapToProviderWatchlistDto(ProviderWatchlist providerWatchlist) {
        return new ProviderWatchlistDto(
                providerWatchlist.getId(),
                providerWatchlist.getUsername(),
                providerWatchlist.getEmail(),
                providerWatchlist.getImdbId(),
                tmdbService.findNameBy(
                        Optional.ofNullable(providerWatchlist.getImdbId()).orElse("-1")),
                Optional.ofNullable(providerWatchlist.getCountry())
                        .map(SupportedCountries::getCountryName)
                        .orElse(SupportedCountries.DEFAULT.getCountryName()),
                providerWatchlist.getProviderType().getTmdbName()
        );
    }

    public List<ProviderWatchlist> mapToProviderWatchlistList(List<ProviderWatchlistDto> providerWatchlistDtoList) {
        return providerWatchlistDtoList.stream()
                .map(this::mapToProviderWatchlist)
                .collect(Collectors.toList());
    }

    public List<ProviderWatchlistDto> mapToProviderWatchlistDtoList(List<ProviderWatchlist> providerWatchlistList) {
        return providerWatchlistList.stream()
                .map(this::mapToProviderWatchlistDto)
                .collect(Collectors.toList());
    }
}
