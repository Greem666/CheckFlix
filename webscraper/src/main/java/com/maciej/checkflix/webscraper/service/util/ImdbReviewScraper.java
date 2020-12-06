package com.maciej.checkflix.webscraper.service.util;

import com.gargoylesoftware.htmlunit.html.*;
import com.maciej.checkflix.webscraper.client.ImdbClient;
import com.maciej.checkflix.webscraper.domain.ImdbReviewDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ImdbReviewScraper {

    private static final Logger logger = LoggerFactory.getLogger(ImdbReviewScraper.class);

    private static final int REQUIRED_REVIEW_COUNT = 200;
    private static final int JS_ALLOWED_CALLBACK_TIME_MILLIS = 500;

    private static final String XPATH_LOAD_MORE_BUTTON = "//button[@class=\"ipl-load-more__button\"]";
    private static final String XPATH_IMDB_TOTAL_REVIEWS_SPAN = "//div[@class=\"lister\"]//span";
    private static final String XPATH_REVIEW_BLOCK_DIVS = "//div[@class='lister-item-content']";

    private static final String XPATH_REVIEW_RATING_SPAN = "./div[@class=\"ipl-ratings-bar\"]/span/span";
    private static final String XPATH_REVIEW_TITLE_ANCHOR = "./a[@class=\"title\"]";
    private static final String XPATH_REVIEW_AUTHOR_ANCHOR = ".//span[@class=\"display-name-link\"]/a";
    private static final String XPATH_REVIEW_DATE_SPAN = ".//span[@class=\"review-date\"]";
    private static final String XPATH_REVIEW_TEXT_DIV = "./div[@class=\"content\"]/div";

    private final ImdbClient imdbClient;

    public List<ImdbReviewDto> scrapeImdbReviews(String imdbId) throws IOException {
        List<HtmlElement> reviewBlocks = loadImdbReviewsPageAndExpandReviewsSection(imdbId);
        return extractReviewDetails(reviewBlocks);
    }

    @SuppressWarnings("unchecked")
    private List<HtmlElement> loadImdbReviewsPageAndExpandReviewsSection(String imdbId) throws IOException {
        HtmlPage page = imdbClient.getReviewsHtmlPageFor(imdbId);
        logger.info(String.format("Imdb review scraping for %s yielded some JavaScript errors.", imdbId));

        Optional<HtmlButton> loadMoreButton = Optional.ofNullable(page.getFirstByXPath(XPATH_LOAD_MORE_BUTTON));
        int reviewsOnRecord = Integer.parseInt(
                ((HtmlSpan)page.getFirstByXPath(XPATH_IMDB_TOTAL_REVIEWS_SPAN)).asText()
                        .split(" ")[0]
                        .replace(",", "")
        );

        if (loadMoreButton.isPresent()) {
            int reviewsCounter = 0;
            while (reviewsCounter <= REQUIRED_REVIEW_COUNT || reviewsCounter >= reviewsOnRecord) {
                //TODO Not ideal - takes a LONG time to scrape these reviews off IMDB website, and tends to generate
                // duplicate entries(???); Taken care of with use of HashSet later on to collate the data, but ends up
                // not producing required REQUIRED_REVIEW_COUNT because of all duplicates removal.
                HtmlPage pageAfterClick = (HtmlPage)loadMoreButton.get().click();
                imdbClient.makeWebClientWaitForJavaScriptExecution(JS_ALLOWED_CALLBACK_TIME_MILLIS);
                page = pageAfterClick;
                reviewsCounter = page.getByXPath(XPATH_REVIEW_BLOCK_DIVS).size();
            }
        }

        return page.getByXPath(XPATH_REVIEW_BLOCK_DIVS);
    }

    private List<ImdbReviewDto> extractReviewDetails(List<HtmlElement> reviewBlocks) {

        Set<ImdbReviewDto> scrapedReviewsList = new HashSet<>();
        if (reviewBlocks.isEmpty()) {
            return new ArrayList<>();
        } else {
            for (HtmlElement review: reviewBlocks) {
                Optional<HtmlElement> spanRating = Optional.ofNullable(
                        review.getFirstByXPath(XPATH_REVIEW_RATING_SPAN));
                Optional<HtmlElement> aTitle = Optional.ofNullable(
                        review.getFirstByXPath(XPATH_REVIEW_TITLE_ANCHOR));
                Optional<HtmlElement> userName = Optional.ofNullable(
                        review.getFirstByXPath(XPATH_REVIEW_AUTHOR_ANCHOR));
                Optional<HtmlElement> spanReviewDate = Optional.ofNullable(
                        review.getFirstByXPath(XPATH_REVIEW_DATE_SPAN));
                Optional<HtmlElement> reviewText = Optional.ofNullable(
                        review.getFirstByXPath(XPATH_REVIEW_TEXT_DIV));

                ImdbReviewDto userReview = new ImdbReviewDto();
                userReview.setRating(spanRating.map(e -> Integer.parseInt(e.getTextContent())).orElse(5));
                userReview.setTitle(aTitle.map(DomNode::getTextContent).orElse(""));
                userReview.setUsername(userName.map(DomNode::getTextContent).orElse(""));
                userReview.setDate(spanReviewDate.map(DomNode::getTextContent).orElse(""));
                userReview.setReview(reviewText.map(DomNode::getTextContent).orElse(""));

                scrapedReviewsList.add(userReview);
            }
        }
        return new ArrayList<>(scrapedReviewsList);
    }
}
