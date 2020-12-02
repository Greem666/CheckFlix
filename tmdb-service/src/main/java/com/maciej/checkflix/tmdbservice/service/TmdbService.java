package com.maciej.checkflix.tmdbservice.service;

import com.maciej.checkflix.tmdbservice.client.TmdbClient;
import com.maciej.checkflix.tmdbservice.client.util.Type;
import com.maciej.checkflix.tmdbservice.domain.idsearch.IdSearchResultsDto;
import com.maciej.checkflix.tmdbservice.domain.idsearch.TmdbIdMap;
import com.maciej.checkflix.tmdbservice.domain.providersearch.CountryResultDto;
import com.maciej.checkflix.tmdbservice.domain.providersearch.SearchResultsDto;
import com.maciej.checkflix.tmdbservice.domain.reviews.ReviewResultDto;
import com.maciej.checkflix.tmdbservice.mapper.TmdbIdMapper;
import com.maciej.checkflix.tmdbservice.repository.TmdbIdMapRepository;
import com.maciej.checkflix.tmdbservice.service.util.CountrySpecificProviderFactory;
import com.maciej.checkflix.tmdbservice.service.util.SupportedCountries;
import com.maciej.checkflix.tmdbservice.service.util.TmdbIdTypeDto;
import com.maciej.checkflix.tmdbservice.service.util.TmdbLinkFixer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TmdbService {

    private static final Logger logger = LoggerFactory.getLogger(TmdbService.class);

    private final TmdbClient tmdbClient;
    private final CountrySpecificProviderFactory countrySpecificProviderFactory;
    private final TmdbIdMapRepository tmdbIdMapRepository;
    private final TmdbIdMapper tmdbIdMapper;

    public CountryResultDto getProvidersFor(String imdbId, String countryName) {
        TmdbIdTypeDto tmdbId = findTmdbCode(imdbId);

        SearchResultsDto globalResults = tmdbClient.getProviders(tmdbId.getTmdbId(), tmdbId.getType());

        if (tmdbId.getTmdbId() != null) {
            return TmdbLinkFixer.fixLogoLinks(
                    countrySpecificProviderFactory.findLocalProvider(globalResults, countryName)
            );
        } else {
            return new CountryResultDto();
        }
    }

    private TmdbIdTypeDto findTmdbCode(String imdbId) {

        Optional<TmdbIdMap> tmdbIdMapOnRecord = Optional.ofNullable(tmdbIdMapRepository.findByImdbIdEquals(imdbId));

        if (tmdbIdMapOnRecord.isPresent()) {
            logger.info(
                    String.format("Found previously saved mapping for ImdbID: %s --> TmdbID:%s for %s",
                            imdbId,
                            tmdbIdMapOnRecord.get().getTmdbId(),
                            tmdbIdMapOnRecord.get().getTitle())
            );
            return new TmdbIdTypeDto(tmdbIdMapOnRecord.get().getTmdbId(), tmdbIdMapOnRecord.get().getType());

        }

        return queryTmdbApiForId(imdbId);
    }

    private TmdbIdTypeDto queryTmdbApiForId(String imdbId) {
        IdSearchResultsDto candidateIds = tmdbClient.getMovieOrTvTmdbId(imdbId);

        Integer movieIdCandidate = candidateIds.getMovieResults().size() > 0 ?
                candidateIds.getMovieResults().get(0).getId() : null;
        Integer tvIdCandidate = candidateIds.getTvResults().size() > 0 ?
                candidateIds.getTvResults().get(0).getId() : null;

        TmdbIdMap newMapping;
        if (movieIdCandidate != null) {
            newMapping = new TmdbIdMap(
                    imdbId, movieIdCandidate, candidateIds.getMovieResults().get(0).getTitle(), Type.MOVIE
            );
        } else {
            newMapping = new TmdbIdMap(
                    imdbId, tvIdCandidate, candidateIds.getTvResults().get(0).getName(), Type.SERIES
            );
        }
        tmdbIdMapRepository.save(newMapping);

        return movieIdCandidate != null ?
                new TmdbIdTypeDto(movieIdCandidate, Type.MOVIE) :
                new TmdbIdTypeDto(tvIdCandidate, Type.SERIES);
    }

    public List<String> getSupportedCountries() {
        return Stream.of(SupportedCountries.values()).map(SupportedCountries::getCountryName).collect(Collectors.toList());
    }

    public List<ReviewResultDto> getReviewsFor(String imdbId) {
        TmdbIdTypeDto tmdbId = findTmdbCode(imdbId);

        List<ReviewResultDto> allReviews = tmdbClient.getReviews(tmdbId.getTmdbId(), tmdbId.getType());

        return Optional.ofNullable(allReviews).orElse(new ArrayList<>());
    }

    public String findNameBy(String imdbId) {
        Optional<TmdbIdMap> tmdbIdMapOnRecord = Optional.ofNullable(tmdbIdMapRepository.findByImdbIdEquals(imdbId));

        if (tmdbIdMapOnRecord.isPresent()) {
            return tmdbIdMapOnRecord.get().getTitle();
        } else {
            try {
                queryTmdbApiForId(imdbId);
                return tmdbIdMapRepository.findByImdbIdEquals(imdbId).getTitle();
            } catch (NullPointerException e) {
                return "No title found";
            }
        }
    }
}
