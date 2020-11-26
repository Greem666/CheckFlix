package com.maciej.checkflix.service;

import com.maciej.checkflix.domain.omdb.*;
import com.maciej.checkflix.mapper.MovieMapper;
import com.maciej.checkflix.omdb.OmdbClient;
import com.maciej.checkflix.repository.MovieRepository;
import com.maciej.checkflix.repository.MovieSearchResultsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmdbService {

    private static Logger logger = LoggerFactory.getLogger(OmdbService.class);

    @Autowired
    private OmdbClient omdbClient;

    @Autowired
    private MovieSearchResultsRepository movieSearchResultsRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieMapper movieMapper;

    public MovieDetailsDto findMovieDetailsBy(String movieImdbId) {
        return omdbClient.findMovieDetailsBy(movieImdbId);
    }

    public List<MovieDto> findMovie(String movieName, String year, String type) {

        if (movieName == null || movieName.isEmpty()) {
            logger.debug("No movie name provided. Returning empty list.");
            return new ArrayList<>();
        }

        if (year.isEmpty()) {
            logger.debug("No year provided.");
            year = null;
        }

        List<String> supportedTypes = getAvailableTypes();
        if (type.isEmpty() || !supportedTypes.contains(type)) {
            logger.debug("No type provided.");
            type = null;
        }

        List<MovieDto> foundMovieDtoList = checkCachedSearchResults(movieName, year, type);
        if (foundMovieDtoList.isEmpty()) {
            foundMovieDtoList = findMoviesInExternalApiBy(movieName, year, type);
            MovieSearchResult newSearchRecord = new MovieSearchResult(LocalDateTime.now(ZoneId.of("UTC")), movieName);
            saveNewSearchResults(newSearchRecord, foundMovieDtoList);
        }

        return foundMovieDtoList;
    }

    private List<MovieDto> checkCachedSearchResults(final String movieName, final String year, final String type) {
        List<MovieSearchResult> previousSearches = movieSearchResultsRepository.findBySearchTitleEquals(movieName);
        if (previousSearches.size() > 0) {
            previousSearches.sort(Comparator.comparing(MovieSearchResult::getSearchOn).reversed());
            MovieSearchResult latestSearch = previousSearches.get(0);

            if (latestSearch.getSearchOn().isAfter(LocalDateTime.now(ZoneId.of("UTC")).minusHours(1L))) {
                logger.info(
                        String.format(
                                "Found previous search results for movie name \"%s\", year \"%s\", type \"%s\", " +
                                "completed on %s.", movieName, year, type, latestSearch.getSearchOn()
                        )
                );

                return movieMapper.mapToMovieDtoList(latestSearch.getMovies()).stream()
                        .filter(year != null ? e -> e.getYear().equals(year) : e -> true)
                        .filter(type != null ? e -> e.getType().equals(type) : e -> true)
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    private List<MovieDto> findMoviesInExternalApiBy(String movieName, String year, String type) {
        List<MovieDto> newMovieDtoList;
        if (year == null) {
            if (type == null) {
                logger.debug(String.format("Searching for movie by name %s only.", movieName));
                newMovieDtoList = new ArrayList<>(omdbClient.findMoviesByName(movieName).getMovieDtoList());
            } else {
                logger.debug(String.format("Searching for movie by name %s and type %s.", movieName, type));
                newMovieDtoList = omdbClient.findMoviesByNameAndType(movieName, type).getMovieDtoList();
            }
        } else {
            if (type == null) {
                logger.debug(String.format("Searching for movie by name %s and year %s.", movieName, year));
                newMovieDtoList = omdbClient.findMoviesByNameAndYear(movieName, year).getMovieDtoList();
            } else {
                logger.debug(String.format("Searching for movie by name %s, year %s and type %s.", movieName, year, type));
                newMovieDtoList = omdbClient.findMoviesByNameAndYearAndType(movieName, year, type).getMovieDtoList();
            }
        }

        return newMovieDtoList == null ? new ArrayList<>() : newMovieDtoList;
    }

    private void saveNewSearchResults(MovieSearchResult movieSearchResult, List<MovieDto> newMovieDtoList) {
        List<Movie> newMovieList = movieMapper.mapToMovieList(newMovieDtoList);
        movieSearchResult.addMovies(newMovieList);
        movieRepository.saveAll(newMovieList);
        movieSearchResultsRepository.save(movieSearchResult);
    }

    public List<String> getAvailableTypes() {
        return Arrays.stream(Type.values()).map(Type::getLabel).collect(Collectors.toList());
    }
}
