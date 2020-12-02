package com.maciej.checkflix.omdbservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "Title",
        "Year",
        "imdbID",
        "Type",
        "Poster"
})
public class MovieDto {
    @JsonProperty("Title")
    public String title;

    @JsonProperty("Year")
    public String year;

    @JsonProperty("imdbID")
    public String imdbID;

    @JsonProperty("Type")
    public String type;

    @JsonProperty("Poster")
    public String poster;
}
