package com.maciej.checkflix.analytics.client;

import com.maciej.checkflix.analytics.client.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class QuickChartIoClient {
    private static final List<String> POSITIVE_COLORS = Arrays.asList(
            "rgba(17, 164, 14, 1)", "rgba(21, 202, 18, 1)", "rgba(107, 242, 105, 1)");
    private static final List<String> NEUTRAL_COLORS = Arrays.asList(
            "rgba(187, 182, 35, 1)", "rgba(224, 218, 18, 1)", "rgba(253, 245, 2, 1)");
    private static final List<String> NEGATIVE_COLORS = Arrays.asList(
            "rgba(212, 65, 41, 1)", "rgba(222, 43, 14, 1)", "rgba(197, 27, 0, 1)");

    private static final List<String> PIE_CHART_COLORS = Arrays.asList(
            "rgba(21, 202, 18, 1)", "rgba(224, 218, 18, 1)", "rgba(222, 43, 14, 1)");
    private static final List<String> DISTRIBUTION_CHART_COLORS = Arrays.asList(
            "#ff0000", "#ff3b00", "#ff7600", "#ffbc00", "#ffe000",
            "#e4ff00", "#b4ff00", "#7aff00", "#3fff00", "#00ff08");

    @Autowired
    private RestTemplate restTemplate;

    public byte[] getWordCloudForPositiveText(String text) {
        QuickChartWordCloudPayload payload = new QuickChartWordCloudPayload(POSITIVE_COLORS, text);
        return getWordCloudForPayload(payload);
    }

    public byte[] getWordCloudForNeutralText(String text) {
        QuickChartWordCloudPayload payload = new QuickChartWordCloudPayload(NEUTRAL_COLORS, text);
        return getWordCloudForPayload(payload);
    }

    public byte[] getWordCloudForNegativeText(String text) {
        QuickChartWordCloudPayload payload = new QuickChartWordCloudPayload(NEGATIVE_COLORS, text);
        return getWordCloudForPayload(payload);
    }

    private byte[] getWordCloudForPayload(QuickChartWordCloudPayload payload) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<QuickChartWordCloudPayload> request = new HttpEntity<>(payload, headers);

        return restTemplate.postForObject(getWordCloudForTextUri(), request, byte[].class);
    }

    private URI getWordCloudForTextUri() {
        return UriComponentsBuilder.fromHttpUrl("https://quickchart.io/wordcloud").build().encode().toUri();
    }

    public byte[] getReviewRatingsPieChart(List<String> labels, List<Integer> dataPoints) {
        Datasets dataset = new Datasets(dataPoints, "Ratings", PIE_CHART_COLORS);
        Data data = new Data(labels, Collections.singletonList(dataset));
        Chart chart = new Chart("doughnut", data);

        QuickChartPayload payload = new QuickChartPayload();
        payload.setChart(chart);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<QuickChartPayload> request = new HttpEntity<>(payload, headers);

        return restTemplate.postForObject(getReviewRatingsPieChartUri(), request, byte[].class);
    }

    public byte[] getReviewRatingsDistribution(ArrayList<String> labels, ArrayList<Integer> dataPoints) {
        Datasets dataset = new Datasets(dataPoints, "Ratings", DISTRIBUTION_CHART_COLORS);
        Data data = new Data(labels, Collections.singletonList(dataset));
        Chart chart = new Chart("bar", data);

        QuickChartPayload payload = new QuickChartPayload();
        payload.setChart(chart);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<QuickChartPayload> request = new HttpEntity<>(payload, headers);

        return restTemplate.postForObject(getReviewRatingsPieChartUri(), request, byte[].class);
    }

    private URI getReviewRatingsPieChartUri() {
        return UriComponentsBuilder.fromHttpUrl("https://quickchart.io/chart").build().encode().toUri();
    }
}
