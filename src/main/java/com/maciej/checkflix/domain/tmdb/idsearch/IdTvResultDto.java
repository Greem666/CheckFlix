package com.maciej.checkflix.domain.tmdb.idsearch;

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
public class IdTvResultDto {
    @JsonProperty("origin_country")
    private List<String> originCountry;

    @JsonProperty("id")
    private int id;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("name")
    private String name;

    @JsonProperty("first_air_date")
    private String firstAirDate;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_name")
    private String originalName;
}
