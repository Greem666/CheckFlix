package com.maciej.checkflix.watchlist.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CheckflixConfig {
    @Value("${watchflix.api.url}")
    private String url;

    @Value("${watchflix.api.port}")
    private String port;
}
