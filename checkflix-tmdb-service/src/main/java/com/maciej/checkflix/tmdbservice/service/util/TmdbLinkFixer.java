package com.maciej.checkflix.tmdbservice.service.util;

import com.maciej.checkflix.tmdbservice.domain.providersearch.CountryResultDto;

import java.util.Collections;
import java.util.Optional;

public class TmdbLinkFixer {

    private static final String TMDB_IMG_PATH = "https://image.tmdb.org/t/p/w500";

    public static CountryResultDto fixLogoLinks(CountryResultDto countryResultDto) {
        Optional.ofNullable(countryResultDto.getFlatrate())
                .orElse(Collections.emptyList())
                .forEach(e -> e.setLogoPath(TMDB_IMG_PATH + e.getLogoPath()));
        Optional.ofNullable(countryResultDto.getBuy())
                .orElse(Collections.emptyList())
                .forEach(e -> e.setLogoPath(TMDB_IMG_PATH + e.getLogoPath()));
        Optional.ofNullable(countryResultDto.getRent())
                .orElse(Collections.emptyList())
                .forEach(e -> e.setLogoPath(TMDB_IMG_PATH + e.getLogoPath()));

        return countryResultDto;
    }
}
