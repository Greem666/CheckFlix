package com.maciej.checkflix.tmdbservice.service.util;

import com.maciej.checkflix.tmdbservice.client.util.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TmdbIdTypeDto {
    private Integer tmdbId;
    private Type type;
}
