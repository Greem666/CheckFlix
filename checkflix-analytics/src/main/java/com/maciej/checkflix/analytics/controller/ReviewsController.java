package com.maciej.checkflix.analytics.controller;

import com.maciej.checkflix.analytics.service.QuickChartIoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class ReviewsController {

    @Autowired
    private QuickChartIoService quickChartIoService;

    @RequestMapping(method = RequestMethod.GET, value = "/analyse/reviews/positive")
    public ResponseEntity<byte[]> getPositiveReviewsWordCloud(@RequestParam String imdbId) {
        return getChart(quickChartIoService.getWordCloudForPositiveReviews(imdbId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/analyse/reviews/neutral")
    public ResponseEntity<byte[]> getNeutralReviewsWordCloud(@RequestParam String imdbId) {
        return getChart(quickChartIoService.getWordCloudForNeutralReviews(imdbId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/analyse/reviews/negative")
    public ResponseEntity<byte[]> getNegativeReviewsWordCloud(@RequestParam String imdbId) {
        return getChart(quickChartIoService.getWordCloudForNegativeReviews(imdbId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/analyse/reviews/piechart")
    public ResponseEntity<byte[]> getReviewRatingsPieChart(@RequestParam String imdbId) {
        return getChart(quickChartIoService.getReviewRatingsPieChart(imdbId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/analyse/reviews/histogram")
    public ResponseEntity<byte[]> getReviewRatingsDistribution(@RequestParam String imdbId) {
        return getChart(quickChartIoService.getReviewRatingsDistribution(imdbId));
    }

    private ResponseEntity<byte[]> getChart(byte[] imageBytesArray) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(
                imageBytesArray, headers, HttpStatus.OK);
        return responseEntity;
    }
}
