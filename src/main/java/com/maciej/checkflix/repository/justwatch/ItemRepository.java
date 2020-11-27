package com.maciej.checkflix.repository.justwatch;

import com.maciej.checkflix.domain.justwatch.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ItemRepository extends CrudRepository<Item, Long> {
}
