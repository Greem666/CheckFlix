package com.maciej.checkflix.analytics.service;

import com.maciej.checkflix.analytics.client.ImdbIdClient;
import com.maciej.checkflix.analytics.client.QuickChartIoClient;
import com.maciej.checkflix.analytics.domain.reviews.ImdbReviewDto;
import com.maciej.checkflix.analytics.service.util.TextCleaner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class QuickChartIoService {

    private static final int POSITIVE_RATING_THRESHOLD = 8;
    private static final int NEUTRAL_RATING_THRESHOLD = 5;

    private final ImdbIdClient imdbIdClient;
    private final QuickChartIoClient quickChartIoClient;
    private final TextCleaner textCleaner;

    //TODO: Positive/Neutral/Negative text selection should be done with help of a Sentiment Analysis, rather than
    // rating cut-offs. This way, sentences with given sentiments could be compiled into Word Clouds, rather than
    // whole reviews, which are always a mix of good an bad.
    // Consider findings a free Sentiment analysis service which could be integrated into the design!
    public byte[] getWordCloudForPositiveReviews(String imdbId) {
        String text = imdbIdClient.getReviewsFor(imdbId).stream()
                .filter(e -> e.getRating() >= POSITIVE_RATING_THRESHOLD)
                .map(ImdbReviewDto::getReview)
                .collect(Collectors.joining());

        text = textCleaner.cleanText(text);

        return quickChartIoClient.getWordCloudForPositiveText(text);
    }

    public byte[] getWordCloudForNeutralReviews(String imdbId) {
        String text = imdbIdClient.getReviewsFor(imdbId).stream()
                .filter(e -> e.getRating() < POSITIVE_RATING_THRESHOLD && e.getRating() >= NEUTRAL_RATING_THRESHOLD)
                .map(ImdbReviewDto::getReview)
                .collect(Collectors.joining());

        text = textCleaner.cleanText(text);

        return quickChartIoClient.getWordCloudForNeutralText(text);
    }

    public byte[] getWordCloudForNegativeReviews(String imdbId) {
        String text = imdbIdClient.getReviewsFor(imdbId).stream()
                .filter(e -> e.getRating() < NEUTRAL_RATING_THRESHOLD)
                .map(ImdbReviewDto::getReview)
                .collect(Collectors.joining());

        text = textCleaner.cleanText(text);

        return quickChartIoClient.getWordCloudForNegativeText(text);
    }

    public byte[] getReviewRatingsPieChart(String imdbId) {
        final List<ImdbReviewDto> reviews = imdbIdClient.getReviewsFor(imdbId);

        int positiveReviewsCount = (int)reviews.stream()
                .filter(e -> e.getRating() >= POSITIVE_RATING_THRESHOLD)
                .count();
        int neutralReviewsCount = (int)reviews.stream()
                .filter(e -> e.getRating() < POSITIVE_RATING_THRESHOLD && e.getRating() >= NEUTRAL_RATING_THRESHOLD)
                .count();
        int negativeReviewsCount = (int)reviews.stream()
                .filter(e -> e.getRating() < NEUTRAL_RATING_THRESHOLD)
                .count();

        return quickChartIoClient.getReviewRatingsPieChart(
                Arrays.asList("Positive reviews", "Neutral reviews", "Negative reviews"),
                Arrays.asList(positiveReviewsCount, neutralReviewsCount, negativeReviewsCount)
        );
    }

    public byte[] getReviewRatingsDistribution(String imdbId) {
        final List<ImdbReviewDto> reviews = imdbIdClient.getReviewsFor(imdbId);

        Map<String, Integer> ratingCounts = new HashMap<>();

        IntStream.rangeClosed(1, 10).forEach(i -> {
            ratingCounts.put(
                    String.valueOf(i),
                    (int)reviews.stream()
                    .filter(e -> e.getRating() == i)
                    .count()
            );
        });

        return quickChartIoClient.getReviewRatingsDistribution(
                new ArrayList<>(ratingCounts.keySet()),
                new ArrayList<>(ratingCounts.values())
        );
    }
}
