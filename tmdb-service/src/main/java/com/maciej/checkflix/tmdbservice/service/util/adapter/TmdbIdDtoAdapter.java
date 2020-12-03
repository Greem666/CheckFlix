package com.maciej.checkflix.tmdbservice.service.util.adapter;

import com.maciej.checkflix.tmdbservice.client.TmdbClient;
import com.maciej.checkflix.tmdbservice.client.util.Type;
import com.maciej.checkflix.tmdbservice.domain.idsearch.IdSearchResultsDto;
import com.maciej.checkflix.tmdbservice.domain.idsearch.TmdbIdMap;
import com.maciej.checkflix.tmdbservice.repository.TmdbIdMapRepository;
import com.maciej.checkflix.tmdbservice.service.TmdbService;
import com.maciej.checkflix.tmdbservice.service.util.TmdbIdMapUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TmdbIdDtoAdapter extends ImdbIdDto {

    private static final Logger logger = LoggerFactory.getLogger(TmdbIdDtoAdapter.class);

    @Autowired
    private TmdbIdMapRepository tmdbIdMapRepository;

    @Autowired
    private TmdbIdMapUpdater tmdbIdMapUpdater;

    @Override
    public TmdbIdTypeDto getMovieInfo() {
        return queryLocalDbForId(this.getImdbId());
    }

    private TmdbIdTypeDto queryLocalDbForId(String imdbId) {
        Optional<TmdbIdMap> tmdbIdMapOnRecord = Optional.ofNullable(tmdbIdMapRepository.findByImdbIdEquals(imdbId));

        if (tmdbIdMapOnRecord.isPresent()) {
            logger.info(
                    String.format("Found previously saved mapping for ImdbID: %s --> TmdbID:%s for %s",
                            imdbId,
                            tmdbIdMapOnRecord.get().getTmdbId(),
                            tmdbIdMapOnRecord.get().getTitle()));
            return new TmdbIdTypeDto(tmdbIdMapOnRecord.get().getTmdbId(), tmdbIdMapOnRecord.get().getType());
        }

        return queryTmdbApiForId(imdbId);
    }

    private TmdbIdTypeDto queryTmdbApiForId(String imdbId) {
        logger.info(
                String.format("No previous records of TMDB ID for IMDBID %s were found. Querying TMDB API...", imdbId));
        TmdbIdMap newTmdbIdMap = tmdbIdMapUpdater.updateTmdbIdMappingFromExternalApiFor(imdbId);

        return new TmdbIdTypeDto(newTmdbIdMap.getTmdbId(), Type.MOVIE);
    }


}