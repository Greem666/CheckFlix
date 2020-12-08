package com.maciej.checkflix.webscraper.service.util;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.maciej.checkflix.webscraper.client.ImdbClient;
import com.maciej.checkflix.webscraper.domain.ImdbReviewDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ImdbReviewScraperTestSuite {

    private static final String XPATH_LOAD_MORE_BUTTON = "//button[@class=\"ipl-load-more__button\"]";
    private static final String XPATH_IMDB_TOTAL_REVIEWS_SPAN = "//div[@class=\"lister\"]//span";
    private static final String XPATH_REVIEW_BLOCK_DIVS = "//div[@class='lister-item-content']";

    private static final String XPATH_REVIEW_RATING_SPAN = "./div[@class=\"ipl-ratings-bar\"]/span/span";
    private static final String XPATH_REVIEW_TITLE_ANCHOR = "./a[@class=\"title\"]";
    private static final String XPATH_REVIEW_AUTHOR_ANCHOR = ".//span[@class=\"display-name-link\"]/a";
    private static final String XPATH_REVIEW_DATE_SPAN = ".//span[@class=\"review-date\"]";
    private static final String XPATH_REVIEW_TEXT_DIV = "./div[@class=\"content\"]/div";

    @InjectMocks
    private ImdbReviewScraper imdbReviewScraper;

    @Mock
    private ImdbClient imdbClient;

    @Test
    public void shouldReturnNoReviews() throws IOException {
        // Given
        HtmlPage webPage = mock(HtmlPage.class);

        when(imdbClient.getReviewsHtmlPageFor(anyString())).thenReturn(webPage);
        when(webPage.getFirstByXPath(XPATH_LOAD_MORE_BUTTON)).thenReturn(null);
        when(webPage.getFirstByXPath(XPATH_IMDB_TOTAL_REVIEWS_SPAN)).thenReturn(null);
        when(webPage.getByXPath(XPATH_REVIEW_BLOCK_DIVS)).thenReturn(new ArrayList<>());

        // When
        List<ImdbReviewDto> result = imdbReviewScraper.scrapeImdbReviews("testImdbId");

        // Then
        Assert.assertEquals(0, result.size());
        verify(imdbClient, times(1)).getReviewsHtmlPageFor(anyString());
        verify(imdbClient, never()).makeWebClientWaitForJavaScriptExecution(anyInt());
    }

    @Test
    public void shouldReturnReviews() throws IOException {
        // Given
        HtmlPage webPage = mock(HtmlPage.class);
        HtmlElement htmlElement = mock(HtmlElement.class);

        when(imdbClient.getReviewsHtmlPageFor(anyString())).thenReturn(webPage);
        when(webPage.getFirstByXPath(XPATH_LOAD_MORE_BUTTON)).thenReturn(null);
        when(webPage.getFirstByXPath(XPATH_IMDB_TOTAL_REVIEWS_SPAN)).thenReturn(null);
        when(webPage.getByXPath(XPATH_REVIEW_BLOCK_DIVS)).thenReturn(
                Collections.singletonList(htmlElement));

        when(htmlElement.getFirstByXPath(XPATH_REVIEW_RATING_SPAN)).thenReturn(null);
        when(htmlElement.getFirstByXPath(XPATH_REVIEW_TITLE_ANCHOR)).thenReturn(null);
        when(htmlElement.getFirstByXPath(XPATH_REVIEW_AUTHOR_ANCHOR)).thenReturn(null);
        when(htmlElement.getFirstByXPath(XPATH_REVIEW_DATE_SPAN)).thenReturn(null);
        when(htmlElement.getFirstByXPath(XPATH_REVIEW_TEXT_DIV)).thenReturn(null);

        // When
        List<ImdbReviewDto> result = imdbReviewScraper.scrapeImdbReviews("testImdbId");

        // Then
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(5, result.get(0).getRating());
        Assert.assertEquals("", result.get(0).getTitle());
        Assert.assertEquals("", result.get(0).getUsername());
        Assert.assertEquals("", result.get(0).getDate());
        Assert.assertEquals("", result.get(0).getReview());
        verify(imdbClient, times(1)).getReviewsHtmlPageFor(anyString());
        verify(imdbClient, never()).makeWebClientWaitForJavaScriptExecution(anyInt());
    }
}