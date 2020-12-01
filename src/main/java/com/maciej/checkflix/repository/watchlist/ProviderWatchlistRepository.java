package com.maciej.checkflix.repository.watchlist;

import com.maciej.checkflix.domain.watchlist.ProviderWatchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProviderWatchlistRepository extends CrudRepository<ProviderWatchlist, Long> {

    @Override
    List<ProviderWatchlist> findAll();

    Optional<ProviderWatchlist> findByEmailAndImdbId(String email, String imdbId);

    void deleteByEmailAndImdbId(String email, String imdbId);

    @Query(
            "SELECT a " +
            "FROM ProviderWatchlist a " +
            "LEFT JOIN TmdbIdMap b ON a.imdbId = b.imdbId " +
            "WHERE a.username LIKE %:PHRASE% OR " +
                    "a.email LIKE %:PHRASE% OR " +
                    "b.title LIKE %:PHRASE%"
    )
    List<ProviderWatchlist> findBySearchPhrase(@Param("PHRASE") String phrase);
}