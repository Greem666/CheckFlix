package com.maciej.checkflix.analytics.controller;

import com.maciej.checkflix.analytics.client.QuickChartIoClient;
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
    private QuickChartIoClient quickChartIoClient;

    @RequestMapping(method = RequestMethod.GET, value = "/analyse/reviews")
    public ResponseEntity<byte[]> getWordCloud(@RequestParam String imdbId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(
                quickChartIoClient.getWordCloudForText(imdbId), headers, HttpStatus.OK);
        return responseEntity;
    }

    // endpoint for piechart with positive-neutral-negative reviews breakdown
    // endpoint for wordcloud with positive reviews phrases
    // endpoint for wordcloud with neutral reviews phrases
    // endpoint for wordcloud with negative reviews phrases
}
