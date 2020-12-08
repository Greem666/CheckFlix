package com.maciej.checkflix.watchlist.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AutomatedMailsConfig {

    @Value("${admin.email}")
    private String adminEmail;
}
