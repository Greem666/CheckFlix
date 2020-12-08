package com.maciej.checkflix.omdbservice.repository;

import com.maciej.checkflix.omdbservice.domain.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface MovieRepository extends CrudRepository<Movie, Long> {
}
