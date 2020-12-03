package com.maciej.checkflix.watchlist.service.util;

import com.maciej.checkflix.watchlist.domain.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchCriteria {
    private String email;
    private String imdbId;
    private SupportedCountries country;
    private ProviderType providerType;
}
