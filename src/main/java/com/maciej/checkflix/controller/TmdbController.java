package com.maciej.checkflix.controller;

import com.maciej.checkflix.domain.tmdb.providersearch.CountryResultDto;
import com.maciej.checkflix.domain.tmdb.reviews.ReviewResultDto;
import com.maciej.checkflix.service.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/tmdb")
public class TmdbController {

    @Autowired
    private TmdbService tmdbService;

    @RequestMapping(method = RequestMethod.GET, value = "/providers")
    public CountryResultDto getMoviesBy(@RequestParam String movieImdbId, @RequestParam String countryName) {
        return tmdbService.getProvidersFor(movieImdbId, countryName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/providers/countries")
    public List<String> getSupportedProviderCountries() {
        return tmdbService.getSupportedCountries();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/reviews")
    public List<ReviewResultDto> getReviewsFor(@RequestParam String imdbId) {
        return tmdbService.getReviewsFor(imdbId);
    }
}
