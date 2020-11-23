package com.maciej.checkflix.repository;

import com.maciej.checkflix.domain.omdb.MovieSearchResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MovieSearchResultsRepository extends CrudRepository<MovieSearchResult, Long> {
    List<MovieSearchResult> findBySearchTitleEquals(String title);
}
