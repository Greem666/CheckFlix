package com.maciej.checkflix.webscraper.controller;

import com.maciej.checkflix.webscraper.domain.ImdbReviewDto;
import com.maciej.checkflix.webscraper.service.ImdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/imdb")
public class ImdbController {

    @Autowired
    private ImdbService imdbService;

    @RequestMapping(method = RequestMethod.GET, value = "/reviews")
    public List<ImdbReviewDto> getImdbReviewsFor(@RequestParam String imdbId) throws IOException {
        return imdbService.getReviewsHtmlPageFor(imdbId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/reviews/cache")
    public void prepareCachedImdbReviewsFor(@RequestParam String imdbId) throws IOException {
        // Needed to cut down user wait time when pinging getImdbReviewsFor(String imdbId) endpoint for the first time
        imdbService.prepareCachedImdbReviewsFor(imdbId);
    }
}
