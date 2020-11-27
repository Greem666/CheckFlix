package com.maciej.checkflix.repository.justwatch;

import com.maciej.checkflix.domain.justwatch.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OfferRepository extends CrudRepository<Offer, Long> {
}
