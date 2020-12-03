package com.maciej.checkflix.tmdbservice.service;

import com.maciej.checkflix.tmdbservice.client.TmdbClient;
import com.maciej.checkflix.tmdbservice.client.util.Type;
import com.maciej.checkflix.tmdbservice.domain.idsearch.IdSearchResultsDto;
import com.maciej.checkflix.tmdbservice.domain.idsearch.TmdbIdMap;
import com.maciej.checkflix.tmdbservice.domain.providersearch.CountryResultDto;
import com.maciej.checkflix.tmdbservice.domain.providersearch.SearchResultsDto;
import com.maciej.checkflix.tmdbservice.domain.reviews.ReviewResultDto;
import com.maciej.checkflix.tmdbservice.repository.TmdbIdMapRepository;
import com.maciej.checkflix.tmdbservice.service.util.*;
import com.maciej.checkflix.tmdbservice.service.util.adapter.ImdbIdDto;
import com.maciej.checkflix.tmdbservice.service.util.adapter.TmdbIdDtoAdapter;
import com.maciej.checkflix.tmdbservice.service.util.adapter.TmdbIdTypeDto;
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
    private final TmdbIdMapUpdater tmdbIdMapUpdater;
    private final TmdbIdDtoAdapter tmdbIdTypeDtoAdapter;

    public CountryResultDto getProvidersFor(String imdbId, String countryName) {
        logger.info(String.format("Grabbing providers for ImdbID: %s in %s", imdbId, countryName));
        TmdbIdTypeDto tmdbIdTypeDto = convertImdbIdToTmdbId(imdbId);

        SearchResultsDto globalResults = tmdbClient.getProviders(tmdbIdTypeDto.getTmdbId(), tmdbIdTypeDto.getType());

        if (tmdbIdTypeDto.getTmdbId() != null) {
            return TmdbLinkFixer.fixLogoLinks(
                    countrySpecificProviderFactory.findLocalProvider(globalResults, countryName));
        } else {
            return new CountryResultDto();
        }
    }

    public List<String> getSupportedCountries() {
        logger.info("Fetching supported provider countries list...");
        return Stream.of(SupportedCountries.values()).map(SupportedCountries::getCountryName).collect(Collectors.toList());
    }

    public List<ReviewResultDto> getReviewsFor(String imdbId) {
        logger.info(String.format("Grabbing reviews for imdbID: %s", imdbId));
        TmdbIdTypeDto tmdbId = convertImdbIdToTmdbId(imdbId);

        List<ReviewResultDto> allReviews = tmdbClient.getReviews(tmdbId.getTmdbId(), tmdbId.getType());

        return Optional.ofNullable(allReviews).orElse(new ArrayList<>());
    }

    public String findNameBy(String imdbId) {
        logger.info(String.format("Finding title name for reviews for ImdbID: %s", imdbId));
        Optional<TmdbIdMap> tmdbIdMapOnRecord = Optional.ofNullable(tmdbIdMapRepository.findByImdbIdEquals(imdbId));

        if (tmdbIdMapOnRecord.isPresent()) {
            return tmdbIdMapOnRecord.get().getTitle();
        } else {
            try {
                tmdbIdMapUpdater.updateTmdbIdMappingFromExternalApiFor(imdbId);
                return tmdbIdMapRepository.findByImdbIdEquals(imdbId).getTitle();
            } catch (NullPointerException e) {
                return "No title found";
            }
        }
    }

    private TmdbIdTypeDto convertImdbIdToTmdbId(String imdbId) {
        tmdbIdTypeDtoAdapter.setImdbId(imdbId);
        return tmdbIdTypeDtoAdapter.getMovieInfo();
    }
    
    


}
