package com.maciej.checkflix.controller;

import com.maciej.checkflix.clients.JustWatchClient;
import com.maciej.checkflix.domain.justwatch.SearchResultsDto;
import com.maciej.checkflix.domain.omdb.MovieDto;
import com.maciej.checkflix.service.JustWatchService;
import com.maciej.checkflix.service.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/jw")
public class JustWatchController {

    @Autowired
    private JustWatchClient justWatchClient;

    @Autowired
    private JustWatchService justWatchService;

    @RequestMapping(method = RequestMethod.GET, value = "/movies")
    public SearchResultsDto getMoviesBy(@RequestParam String movieName) {
        return justWatchClient.findMoviesByName(movieName, "en_AU");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/moviess")
    public SearchResultsDto getMoviesByy(@RequestParam String movieName, @RequestParam String locale) {
        return justWatchService.findMovie(movieName, locale);
    }
}
