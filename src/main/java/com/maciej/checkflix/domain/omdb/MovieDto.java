package com.maciej.checkflix.domain.omdb;

import com.fasterxml.jackson.annotation.*;
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

    @Getter
    public enum Types {
        MOVIE("movie"), SERIES("series"), EPISODE("episode");

        public final String label;

        private Types(String label) {
            this.label = label;
        }
    }
}
