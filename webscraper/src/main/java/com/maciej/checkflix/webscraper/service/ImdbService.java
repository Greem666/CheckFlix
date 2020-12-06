package com.maciej.checkflix.webscraper.service;

import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.html.*;
import com.maciej.checkflix.webscraper.client.ImdbClient;
import com.maciej.checkflix.webscraper.domain.ImdbReview;
import com.maciej.checkflix.webscraper.domain.ImdbReviewDto;
import com.maciej.checkflix.webscraper.mapper.ImdbReviewMapper;
import com.maciej.checkflix.webscraper.repository.ImdbReviewRepository;
import com.maciej.checkflix.webscraper.service.util.ImdbReviewScraper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImdbService {
    private static final Logger logger = LoggerFactory.getLogger(ImdbService.class);

    private final ImdbReviewScraper imdbReviewScraper;
    private final ImdbReviewRepository imdbReviewRepository;
    private final ImdbReviewMapper imdbReviewMapper;

    public void prepareCachedImdbReviewsFor(String imdbId) throws IOException {
        Optional<List<ImdbReview>> previouslyScrapedReviews = Optional.ofNullable(
                imdbReviewRepository.findByImdbIdOrderByScrapedOnDesc(imdbId));

        if (previouslyScrapedReviews.isPresent()) {
            try {
                if (previouslyScrapedReviews.get().isEmpty()) {
                    refreshDbReviewsCacheFor(imdbId);
                }
                if (!previouslyScrapedReviews.get().isEmpty()) {
                    if (previouslyScrapedReviews.get().get(0).getScrapedOn().isBefore(LocalDateTime.now().minusDays(30))) {
                        refreshDbReviewsCacheFor(imdbId);
                    }
                }
            } catch (IOException e) {
                logger.error("There was an error while preparing scraped Imdb reviews cache for ImdbID: " + imdbId);
            }
        }
    }

    public List<ImdbReviewDto> getReviewsHtmlPageFor(String imdbId) {
        Optional<List<ImdbReview>> previouslyScrapedReviews = Optional.ofNullable(
                imdbReviewRepository.findByImdbIdOrderByScrapedOnDesc(imdbId));

        if (previouslyScrapedReviews.isPresent()) {
            if (!previouslyScrapedReviews.get().isEmpty()) {
                if (previouslyScrapedReviews.get().get(0).getScrapedOn().isAfter(LocalDateTime.now().minusDays(30))) {
                    return imdbReviewMapper.mapToImdbReviewDtoList(previouslyScrapedReviews.get());
                }
            }
        }

        try {
            refreshDbReviewsCacheFor(imdbId);
            return imdbReviewMapper.mapToImdbReviewDtoList(imdbReviewRepository.findByImdbId(imdbId));
        } catch (IOException e) {
            logger.error("There was an error while trying to scrape Imdb reviews for ImdbID: " + imdbId);
            return new ArrayList<>();
        }
    }

    private void refreshDbReviewsCacheFor(String imdbId) throws IOException {
        imdbReviewRepository.deleteAllByImdbId(imdbId);
        List<ImdbReviewDto> newlyScrapedReviews = imdbReviewScraper.scrapeImdbReviews(imdbId);
        List<ImdbReviewDto> filteredNewReviews = newlyScrapedReviews.stream()  // ImdbReview entity limits it at 20000
                .filter(e -> e.getReview().length() < 20000)
                .collect(Collectors.toList());
        imdbReviewRepository.saveAll(imdbReviewMapper.mapToImdbReviewList(filteredNewReviews, imdbId));
    }


}
