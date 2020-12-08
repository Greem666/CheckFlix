package com.maciej.checkflix.tmdbservice.service.util.adapter;

import com.maciej.checkflix.tmdbservice.client.util.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TmdbIdTypeDto implements Id {
    private Integer tmdbId;
    private Type type;

    @Override
    public Id getMovieInfo() {
        return new TmdbIdTypeDto(this.getTmdbId(), this.getType());
    }
}
