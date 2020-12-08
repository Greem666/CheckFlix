package com.maciej.checkflix.omdbservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "Search",
        "totalResults",
        "Response"
})
public class MovieNameSearchDto {
    @JsonProperty("Search")
    public List<MovieDto> movieDtoList = null;

    @JsonProperty("totalResults")
    public String totalResults;

    @JsonProperty("Response")
    public String response;
}
