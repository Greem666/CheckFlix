package com.maciej.checkflix.watchlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProviderWatchlistDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("imdbId")
    private String imdbId;

    @JsonProperty("movieName")
    private String movieName;

    @JsonProperty("country")
    private String country;

    @JsonProperty("providerType")
    private String providerType;

    @Override
    public String toString() {
        return movieName + " for " + providerType + " - " + username + " in " + country;
    }
}
