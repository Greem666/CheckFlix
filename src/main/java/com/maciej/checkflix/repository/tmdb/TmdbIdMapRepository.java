package com.maciej.checkflix.repository.tmdb;

import com.maciej.checkflix.domain.tmdb.idsearch.TmdbIdMap;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TmdbIdMapRepository extends CrudRepository<TmdbIdMap, Long> {

    TmdbIdMap findByImdbIdEquals(String imdbId);
}
