package com.maciej.checkflix.tmdbservice.service.util;

import com.maciej.checkflix.tmdbservice.client.TmdbClient;
import com.maciej.checkflix.tmdbservice.client.util.Type;
import com.maciej.checkflix.tmdbservice.domain.idsearch.IdSearchResultsDto;
import com.maciej.checkflix.tmdbservice.domain.idsearch.TmdbIdMap;
import com.maciej.checkflix.tmdbservice.repository.TmdbIdMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TmdbIdMapUpdater {

    @Autowired
    private TmdbIdMapRepository tmdbIdMapRepository;
    @Autowired
    private TmdbClient tmdbClient;

    public TmdbIdMap updateTmdbIdMappingFromExternalApiFor(String imdbId) {
        IdSearchResultsDto candidateIds = tmdbClient.getMovieOrTvTmdbId(imdbId);

        Integer movieIdCandidate = candidateIds.getMovieResults().size() > 0 ?
                candidateIds.getMovieResults().get(0).getId() : null;
        Integer tvIdCandidate = candidateIds.getTvResults().size() > 0 ?
                candidateIds.getTvResults().get(0).getId() : null;
        //TODO nie iteruj po obu null`ach! przyklad: Vader: A Star Wars Theory Fan Series
        TmdbIdMap newMapping;
        if (movieIdCandidate != null) {
            newMapping = new TmdbIdMap(
                    imdbId, movieIdCandidate, candidateIds.getMovieResults().get(0).getTitle(), Type.MOVIE
            );
            tmdbIdMapRepository.save(newMapping);
        } else if (tvIdCandidate != null) {
            newMapping = new TmdbIdMap(
                    imdbId, tvIdCandidate, candidateIds.getTvResults().get(0).getName(), Type.SERIES
            );
            tmdbIdMapRepository.save(newMapping);
        } else {
            newMapping = new TmdbIdMap();
        }

        return newMapping;
    }
}
