package com.maciej.checkflix.service.util;

import com.maciej.checkflix.domain.omdb.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TmdbIdTypeDto {
    private Integer tmdbId;
    private Type type;
}
