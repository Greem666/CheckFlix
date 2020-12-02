package com.maciej.checkflix.watchlist.client;

import com.maciej.checkflix.watchlist.config.CheckflixConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TmdbClient {

    private final RestTemplate restTemplate;
    private final CheckflixConfig checkflixConfig;

    public String findNameBy(String imdbId) {
        Optional<String> searchResults = Optional.ofNullable(
                restTemplate.getForObject(findNameByUri(imdbId), String.class)
        );

        return searchResults.orElse(null);
    }

    private URI findNameByUri(String imdbId) {
        return UriComponentsBuilder.fromHttpUrl(checkflixConfig.getUrl())
                .port(checkflixConfig.getPort())
                .pathSegment("tmdb-service", "v1", "name", imdbId)
                .build()
                .encode()
                .toUri();
    }
}
