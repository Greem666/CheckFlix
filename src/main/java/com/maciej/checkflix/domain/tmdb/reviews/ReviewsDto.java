package com.maciej.checkflix.domain.tmdb.reviews;

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
public class ReviewsDto {
    @JsonProperty("id")
    public Long id;

    @JsonProperty("page")
    public Long page;

    @JsonProperty("results")
    public List<ReviewResultDto> results = null;

    @JsonProperty("total_pages")
    public Long totalPages;

    @JsonProperty("total_results")
    public Long totalResults;

}
