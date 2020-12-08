package com.maciej.checkflix.omdbservice.repository;

import com.maciej.checkflix.omdbservice.domain.MovieSearchResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MovieSearchResultsRepository extends CrudRepository<MovieSearchResult, Long> {
    List<MovieSearchResult> findBySearchTitleEquals(String title);
}
