package com.maciej.checkflix.mapper.watchlist;

import com.maciej.checkflix.domain.watchlist.ProviderWatchlist;
import com.maciej.checkflix.domain.watchlist.ProviderWatchlistDto;
import com.maciej.checkflix.service.TmdbService;
import com.maciej.checkflix.service.util.SupportedCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
                SupportedCountries.from(providerWatchlistDto.getCountry())
        );
    }

    public ProviderWatchlistDto mapToProviderWatchlistDto(ProviderWatchlist providerWatchlist) {
        return new ProviderWatchlistDto(
                providerWatchlist.getId(),
                providerWatchlist.getUsername(),
                providerWatchlist.getEmail(),
                providerWatchlist.getImdbId(),
                tmdbService.findNameBy(providerWatchlist.getImdbId()),
                providerWatchlist.getCountry().getCountryName()
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
