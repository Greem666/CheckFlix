package com.maciej.checkflix.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TmdbConfig {
    @Value("${tmdb.api.url}")
    private String apiUrl;

    @Value("${tmdb.api.key}")
    private String apiKey;
}
