package com.maciej.checkflix.watchlist.domain;

import com.maciej.checkflix.watchlist.service.util.SupportedCountries;
import lombok.Getter;

@Getter
public enum ProviderType {
    STREAMING("flatrate"), BUY("buy"), RENT("rent");

    private String tmdbName;

    ProviderType(String tmdbName) {
        this.tmdbName = tmdbName;
    }

    public static ProviderType from(String text) {
        for (ProviderType type : ProviderType.values()) {
            if (type.tmdbName.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }

}
