package com.maciej.checkflix.tmdbservice.controller;

import com.maciej.checkflix.tmdbservice.domain.providersearch.CountryResultDto;
import com.maciej.checkflix.tmdbservice.domain.reviews.ReviewResultDto;
import com.maciej.checkflix.tmdbservice.service.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
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

    @RequestMapping(method = RequestMethod.GET, value = "/name/{imdbId}")
    public String getNameFor(@PathVariable String imdbId) {
        return tmdbService.findNameBy(imdbId);
    }
}
