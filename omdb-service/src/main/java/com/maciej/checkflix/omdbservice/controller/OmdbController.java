package com.maciej.checkflix.omdbservice.controller;

import com.maciej.checkflix.omdbservice.domain.MovieDetailsDto;
import com.maciej.checkflix.omdbservice.domain.MovieDto;
import com.maciej.checkflix.omdbservice.service.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class OmdbController {

    @Autowired
    private OmdbService omdbService;

    @RequestMapping(method = RequestMethod.GET, value = "/movies")
    public List<MovieDto> getMoviesBy(@RequestParam String name, @RequestParam String year, @RequestParam String type) {
        return omdbService.findMovie(name, year, type);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/types")
    public List<String> getAvailableTypes() {
        return omdbService.getAvailableTypes();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/movie")
    public MovieDetailsDto getMovieDetails(@RequestParam String movieImdbId) {
        return omdbService.findMovieDetailsBy(movieImdbId);
    }
}
