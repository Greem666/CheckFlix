package com.maciej.checkflix.tmdbservice.service.util.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImdbIdDto implements Id {
    private String imdbId;

    public Id getMovieInfo() {
        return new ImdbIdDto(this.getImdbId());
    }
}
