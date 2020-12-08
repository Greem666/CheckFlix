package com.maciej.checkflix.tmdbservice.domain.idsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdSearchResultsDto {

    @JsonProperty("movie_results")
    private List<IdMovieResultDto> movieResults;

    @JsonProperty("tv_results")
    private List<IdTvResultDto> tvResults;
}
