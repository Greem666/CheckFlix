package com.maciej.checkflix.repository.omdb;

import com.maciej.checkflix.domain.omdb.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface MovieRepository extends CrudRepository<Movie, Long> {
}
