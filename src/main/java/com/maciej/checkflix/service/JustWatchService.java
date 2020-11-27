package com.maciej.checkflix.service;

import com.maciej.checkflix.clients.JustWatchClient;
import com.maciej.checkflix.domain.justwatch.SearchResults;
import com.maciej.checkflix.domain.justwatch.SearchResultsDto;
import com.maciej.checkflix.domain.omdb.Movie;
import com.maciej.checkflix.domain.omdb.MovieDto;
import com.maciej.checkflix.domain.omdb.MovieSearchResult;
import com.maciej.checkflix.mapper.justwatch.SearchResultsMapper;
import com.maciej.checkflix.repository.justwatch.SearchResultsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JustWatchService {

    private static final Logger logger = LoggerFactory.getLogger(JustWatchService.class);

    private final JustWatchClient justWatchClient;
    private final SearchResultsRepository searchResultsRepository;
    private final SearchResultsMapper searchResultsMapper;

    public SearchResultsDto findMovie(String movieName, String locale) {

        if (movieName == null || movieName.isEmpty()) {
            logger.debug("No movie name provided. Returning empty list.");
            return new SearchResultsDto();
        }

        SearchResultsDto foundMovieDtoList = checkCachedSearchResults(movieName);
        if (foundMovieDtoList == null) {
            foundMovieDtoList = findMoviesInExternalApiBy(movieName, locale);
            saveNewSearchResults(foundMovieDtoList, movieName, locale);
        }

        return foundMovieDtoList;
    }

    private SearchResultsDto checkCachedSearchResults(final String movieName) {
        List<SearchResults> previousSearches = searchResultsRepository.findBySearchTitleEquals(movieName);
        if (previousSearches.size() > 0) {
            previousSearches.sort(Comparator.comparing(SearchResults::getSearchOn).reversed());
            SearchResults latestSearch = previousSearches.get(0);

            if (latestSearch.getSearchOn().isAfter(LocalDateTime.now(ZoneId.of("UTC")).minusHours(1L))) {
                logger.info(
                        String.format(
                                "Found previous search results for movie name \"%s\", " +
                                        "completed on %s.", movieName, latestSearch.getSearchOn()
                        )
                );

                return searchResultsMapper.mapToSearchResultsDto(latestSearch);
            }
        }
        return null;
    }

    private SearchResultsDto findMoviesInExternalApiBy(String movieName, String locale) {
        logger.debug(String.format("Searching for movie by name %s only.", movieName));
        SearchResultsDto newSearchResultsDto = justWatchClient.findMoviesByName(movieName, locale);

        return newSearchResultsDto == null ? new SearchResultsDto() : newSearchResultsDto;
    }

    private void saveNewSearchResults(SearchResultsDto searchResultsDto, String movieName, String searchLocale) {
        SearchResults searchResults = searchResultsMapper.mapToSearchResults(searchResultsDto, movieName, searchLocale);
        searchResultsRepository.save(searchResults);
    }
}
