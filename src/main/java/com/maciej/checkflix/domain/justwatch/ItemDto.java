package com.maciej.checkflix.domain.justwatch;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDto {
    @JsonProperty("jw_entity_id")
    public String jwEntityId;

    @JsonProperty("id")
    public Long id;

    @JsonProperty("title")
    public String title;

    @JsonProperty("full_path")
    public String fullPath;

    @JsonProperty("full_paths")
    public FullPathsDto fullPathsDto;

    @JsonProperty("poster")
    public String poster;

    @JsonProperty("original_release_year")
    public Long originalReleaseYear;

    @JsonProperty("tmdb_popularity")
    public Double tmdbPopularity;

    @JsonProperty("object_type")
    public String objectType;

    @JsonProperty("localized_release_date")
    public String localizedReleaseDate;

    @JsonProperty("offers")
    public List<OfferDto> offerDtos = null;

    @JsonProperty("scoring")
    public List<ScoringDto> scoringDto = null;

    @JsonProperty("cinema_release_date")
    public String cinemaReleaseDate;

    @Override
    public String toString() {
        return "ItemDto{" +
                "jwEntityId='" + jwEntityId + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", fullPath='" + fullPath + '\'' +
                ", fullPathsDto=" + fullPathsDto +
                ", poster='" + poster + '\'' +
                ", originalReleaseYear=" + originalReleaseYear +
                ", tmdbPopularity=" + tmdbPopularity +
                ", objectType='" + objectType + '\'' +
                ", localizedReleaseDate='" + localizedReleaseDate + '\'' +
                ", offerDtos=" + offerDtos +
                ", scoringDto=" + scoringDto +
                ", cinemaReleaseDate='" + cinemaReleaseDate + '\'' +
                '}';
    }
}
