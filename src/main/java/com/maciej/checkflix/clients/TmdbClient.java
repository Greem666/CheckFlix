package com.maciej.checkflix.clients;

import com.maciej.checkflix.config.TmdbConfig;
import com.maciej.checkflix.domain.omdb.Type;
import com.maciej.checkflix.domain.tmdb.idsearch.IdSearchResultsDto;
import com.maciej.checkflix.domain.tmdb.providersearch.ProviderSearchOverviewDto;
import com.maciej.checkflix.domain.tmdb.providersearch.SearchResultsDto;
import com.maciej.checkflix.domain.tmdb.reviews.ReviewResultDto;
import com.maciej.checkflix.domain.tmdb.reviews.ReviewsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TmdbClient {

    private final RestTemplate restTemplate;
    private final TmdbConfig tmdbConfig;

    public SearchResultsDto getProviders(int tmdbId, Type tvOrMovie) {
        URI url;
        switch(tvOrMovie) {
            default:
            case MOVIE:
                url = getMovieProvidersUri(tmdbId);
                break;
            case SERIES:
            case EPISODE:
                url = getTvProvidersUri(tmdbId);
                break;
        }
        Optional<ProviderSearchOverviewDto> searchResults = Optional.ofNullable(
                restTemplate.getForObject(
                        url,
                        ProviderSearchOverviewDto.class)
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

    private URI getTvProvidersUri(int movieTmdbId) {
        return UriComponentsBuilder.fromHttpUrl(tmdbConfig.getApiUrl())
                .pathSegment("tv", String.valueOf(movieTmdbId), "watch", "providers")
                .queryParam("api_key", tmdbConfig.getApiKey())
                .build()
                .encode()
                .toUri();
    }

    public IdSearchResultsDto getMovieOrTvTmdbId(String ImdbId) {
        Optional<IdSearchResultsDto> searchResults = Optional.ofNullable(
                restTemplate.getForObject(getMovieOrTvTmdbIdUri(ImdbId), IdSearchResultsDto.class)
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

    public List<ReviewResultDto> getReviews(int tmdbId, Type tvOrMovie) {
        URI url;
        switch(tvOrMovie) {
            default:
            case MOVIE:
                url = getMovieReviewsUri(tmdbId);
                break;
            case SERIES:
            case EPISODE:
                url = getTvReviewsUri(tmdbId);
                break;
        }
        Optional<ReviewsDto> searchResults = Optional.ofNullable(
                restTemplate.getForObject(
                        url,
                        ReviewsDto.class)
        );

        return searchResults.map(ReviewsDto::getResults).orElse(new ArrayList<>());
    }

    private URI getMovieReviewsUri(int tmdbId) {
        return UriComponentsBuilder.fromHttpUrl(tmdbConfig.getApiUrl())
                .pathSegment("movie", String.valueOf(tmdbId), "reviews")
                .queryParam("api_key", tmdbConfig.getApiKey())
                .build()
                .encode()
                .toUri();
    }

    private URI getTvReviewsUri(int tmdbId) {
        return UriComponentsBuilder.fromHttpUrl(tmdbConfig.getApiUrl())
                .pathSegment("tv", String.valueOf(tmdbId), "reviews")
                .queryParam("api_key", tmdbConfig.getApiKey())
                .build()
                .encode()
                .toUri();
    }
}
