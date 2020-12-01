package com.maciej.checkflix.mapper.tmdb;

import com.maciej.checkflix.domain.omdb.Type;
import com.maciej.checkflix.domain.tmdb.idsearch.IdSearchResultsDto;
import com.maciej.checkflix.domain.tmdb.idsearch.TmdbIdMap;
import org.springframework.stereotype.Service;

@Service
public class TmdbIdMapper {

    public TmdbIdMap mapToTmdbIdMap(IdSearchResultsDto searchResultsDto, String imdbId) {
        boolean isMovie = searchResultsDto.getMovieResults().size() > 0;
        return new TmdbIdMap(
                imdbId,
                isMovie ?
                        searchResultsDto.getMovieResults().get(0).getId() :
                        searchResultsDto.getTvResults().get(0).getId(),
                isMovie ?
                        searchResultsDto.getMovieResults().get(0).getTitle() :
                        searchResultsDto.getTvResults().get(0).getName(),
                isMovie ?
                        Type.MOVIE :
                        Type.SERIES
        );
    }
}
