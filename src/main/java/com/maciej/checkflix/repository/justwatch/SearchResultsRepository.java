package com.maciej.checkflix.repository.justwatch;

import com.maciej.checkflix.domain.justwatch.SearchResults;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SearchResultsRepository extends CrudRepository<SearchResults, Long> {

    List<SearchResults> findBySearchTitleEquals(String searchTitle);
}
