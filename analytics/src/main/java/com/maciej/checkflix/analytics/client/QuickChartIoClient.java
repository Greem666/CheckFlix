package com.maciej.checkflix.analytics.client;

import com.maciej.checkflix.analytics.client.util.QuickChartWordCloudPayload;
import com.maciej.checkflix.analytics.domain.reviews.ReviewResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@Service
public class QuickChartIoClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TmdbIdClient tmdbIdClient;

    public byte[] getWordCloudForText(String imdbId) {
        String text = tmdbIdClient.getReviewsFor(imdbId).stream().map(ReviewResultDto::getContent).collect(Collectors.joining());
        QuickChartWordCloudPayload payload = new QuickChartWordCloudPayload(text);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<QuickChartWordCloudPayload> request = new HttpEntity<>(payload, headers);

        return restTemplate.postForObject(getWordCloudForTextUri(), request, byte[].class);
    }

    private URI getWordCloudForTextUri() {
        return UriComponentsBuilder.fromHttpUrl("https://quickchart.io/wordcloud").build().encode().toUri();
    }
}
