package com.maciej.checkflix.analytics.client;

import com.maciej.checkflix.analytics.config.CheckflixConfig;
import com.maciej.checkflix.analytics.domain.reviews.ReviewResultDto;
import com.sun.el.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TmdbIdClient {

    private final RestTemplate restTemplate;
    private final CheckflixConfig checkflixConfig;

    public List<ReviewResultDto> getReviewsFor(String imdbId) {
        Optional<ReviewResultDto[]> searchResults = Optional.ofNullable(
                restTemplate.getForObject(getReviewsForUri(imdbId), ReviewResultDto[].class)
        );

        return searchResults.map(Arrays::asList).orElseGet(ArrayList::new);
    }

    private URI getReviewsForUri(String imdbId) {
        return UriComponentsBuilder.fromHttpUrl(checkflixConfig.getUrl())
                .port(checkflixConfig.getPort())
                .pathSegment("tmdb-service", "v1", "reviews")
                .queryParam("imdbId", imdbId)
                .build()
                .encode()
                .toUri();
    }
}
