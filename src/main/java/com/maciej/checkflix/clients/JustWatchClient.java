package com.maciej.checkflix.clients;

import com.maciej.checkflix.clients.utils.JustWatchPostPayload;
import com.maciej.checkflix.config.JustWatchConfig;
import com.maciej.checkflix.config.OmdbConfig;
import com.maciej.checkflix.domain.justwatch.SearchResultsDto;
import com.maciej.checkflix.domain.omdb.MovieNameSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JustWatchClient {

    private final RestTemplate restTemplate;
    private final JustWatchConfig justWatchConfig;

    public SearchResultsDto findMoviesByName(String movieName, String locale) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(justWatchConfig.getHeaderKey(), justWatchConfig.getHeaderValue());

        JustWatchPostPayload payload = new JustWatchPostPayload();
        payload.setQuery(movieName);
        HttpEntity<JustWatchPostPayload> request = new HttpEntity<>(payload, headers);
        Optional<SearchResultsDto> movieSearchResult = Optional.ofNullable(
                restTemplate.postForObject(findMoviesByNameUri(locale), request, SearchResultsDto.class)
        );

        return movieSearchResult.orElseGet(SearchResultsDto::new);
    }

    private URI findMoviesByNameUri(String locale) {
        return UriComponentsBuilder.fromHttpUrl(justWatchConfig.getApiUrl())
                .path(String.format("titles/%s/popular", locale))
                .encode()
                .build()
                .toUri();
    }
}
