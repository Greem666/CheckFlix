package com.maciej.checkflix.webscraper.client;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ImdbClient {

    private final WebClient webClient;

    public HtmlPage getReviewsHtmlPageFor(String imdbId) throws IOException {
        String url = "https://www.imdb.com/title/" + imdbId + "/reviews";
        return webClient.getPage(url);
    }

    public void makeWebClientWaitForJavaScriptExecution(int timeOutMillis) {
        webClient.waitForBackgroundJavaScript(timeOutMillis * 2);
    }

}
