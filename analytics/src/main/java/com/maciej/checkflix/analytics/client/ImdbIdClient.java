package com.maciej.checkflix.analytics.client;

import com.maciej.checkflix.analytics.config.CheckflixConfig;
import com.maciej.checkflix.analytics.domain.reviews.ImdbReviewDto;
import com.maciej.checkflix.analytics.domain.reviews.ReviewResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImdbIdClient {

    private final RestTemplate restTemplate;
    private final CheckflixConfig checkflixConfig;

    public List<ImdbReviewDto> getReviewsFor(String imdbId) {
        Optional<ImdbReviewDto[]> searchResults = Optional.ofNullable(
                restTemplate.getForObject(getReviewsForUri(imdbId), ImdbReviewDto[].class)
        );

        return searchResults.map(Arrays::asList).orElseGet(ArrayList::new);
    }

    private URI getReviewsForUri(String imdbId) {
        return UriComponentsBuilder.fromHttpUrl(checkflixConfig.getUrl())
                .port(checkflixConfig.getPort())
                .pathSegment("webscraper", "v1", "imdb", "reviews")
                .queryParam("imdbId", imdbId)
                .build()
                .encode()
                .toUri();
    }
}
