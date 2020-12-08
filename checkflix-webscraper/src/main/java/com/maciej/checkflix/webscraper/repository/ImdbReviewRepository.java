package com.maciej.checkflix.webscraper.repository;

import com.maciej.checkflix.webscraper.domain.ImdbReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ImdbReviewRepository extends CrudRepository<ImdbReview, Long> {
    List<ImdbReview> findByImdbId(String imdbId);

    List<ImdbReview> findByImdbIdOrderByScrapedOnDesc(String imdbId);

    void deleteAllByImdbId(String imdbId);
}
