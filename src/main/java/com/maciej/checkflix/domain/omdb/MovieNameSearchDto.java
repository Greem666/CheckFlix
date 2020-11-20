package com.maciej.checkflix.domain.omdb;

import com.fasterxml.jackson.annotation.*;
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
