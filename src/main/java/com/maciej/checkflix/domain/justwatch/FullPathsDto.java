package com.maciej.checkflix.domain.justwatch;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullPathsDto {
    @JsonProperty("MOVIE_DETAIL_OVERVIEW")
    public String movieDetailOverview;
}
