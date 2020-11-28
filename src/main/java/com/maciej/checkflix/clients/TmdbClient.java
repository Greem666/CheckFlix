package com.maciej.checkflix.clients;

import com.maciej.checkflix.config.TmdbConfig;
import com.maciej.checkflix.domain.tmdb.idsearch.IdSearchResultsDto;
import com.maciej.checkflix.domain.tmdb.providersearch.ProviderSearchOverviewDto;
import com.maciej.checkflix.domain.tmdb.providersearch.SearchResultsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TmdbClient {

    private final RestTemplate restTemplate;
    private final TmdbConfig tmdbConfig;

    public SearchResultsDto getProviders(int movieTmdbId) {
        Optional<ProviderSearchOverviewDto> searchResults = Optional.ofNullable(
                restTemplate.getForObject(getMovieProvidersUri(603), ProviderSearchOverviewDto.class)
        );

        return searchResults.map(ProviderSearchOverviewDto::getResults).orElse(new SearchResultsDto());
    }

    private URI getMovieProvidersUri(int movieTmdbId) {
        return UriComponentsBuilder.fromHttpUrl(tmdbConfig.getApiUrl())
                .pathSegment("movie", String.valueOf(movieTmdbId), "watch", "providers")
                .queryParam("api_key", tmdbConfig.getApiKey())
                .build()
                .encode()
                .toUri();
    }

    public IdSearchResultsDto getMovieOrTvTmdbId(String movieImdbId) {
        Optional<IdSearchResultsDto> searchResults = Optional.ofNullable(
                restTemplate.getForObject(getMovieOrTvTmdbIdUri(movieImdbId), IdSearchResultsDto.class)
        );

        return searchResults.orElse(new IdSearchResultsDto());
    }

    private URI getMovieOrTvTmdbIdUri(String movieImdbId) {
        return UriComponentsBuilder.fromHttpUrl(tmdbConfig.getApiUrl())
                .pathSegment("find", movieImdbId)
                .queryParam("api_key", tmdbConfig.getApiKey())
                .queryParam("external_source", "imdb_id")
                .build()
                .encode()
                .toUri();
    }
}
