package com.maciej.checkflix.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JustWatchConfig {
    @Value("${justwatch.api.url}")
    private String apiUrl;

    @Value("${justwatch.api.header.key}")
    private String headerKey;

    @Value("${justwatch.api.header.value}")
    private String headerValue;
}