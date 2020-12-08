package com.maciej.checkflix.watchlist.service;

import com.maciej.checkflix.watchlist.client.TmdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TmdbService {

    private final TmdbClient tmdbClient;

    public String findNameBy(String imdbId) {
        return tmdbClient.findNameBy(imdbId);
    }
}
