package com.maciej.checkflix.service;

import com.maciej.checkflix.domain.omdb.MovieDto;
import com.maciej.checkflix.omdb.OmdbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmdbService {

    @Autowired
    private OmdbClient omdbClient;

    public List<MovieDto> findMovie(String movieName, String year, String type) {
        if (movieName == null) {
            return new ArrayList<>();
        }

        List<String> supportedTypes = getAvailableTypes();
        if (!supportedTypes.contains(type)) {
            type = null;
        }

        if (year == null) {
            if (type == null) {
                return omdbClient.findMoviesByName(movieName).getMovieDtoList();
            } else {
                return omdbClient.findMoviesByNameAndType(movieName, type).getMovieDtoList();
            }
        } else {
            if (type == null) {
                return omdbClient.findMoviesByNameAndYear(movieName, year).getMovieDtoList();
            } else {
                return omdbClient.findMoviesByNameAndYearAndType(movieName, year, type).getMovieDtoList();
            }
        }
    }

    public List<String> getAvailableTypes() {
        return Arrays.stream(MovieDto.Types.values()).map(MovieDto.Types::getLabel).collect(Collectors.toList());
    }
}
