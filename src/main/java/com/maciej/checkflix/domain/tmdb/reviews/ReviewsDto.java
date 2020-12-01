package com.maciej.checkflix.domain.tmdb.reviews;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "page",
        "results",
        "total_pages",
        "total_results"
})
public class Reviews {

    @JsonProperty("id")
    public Long id;
    @JsonProperty("page")
    public Long page;
    @JsonProperty("results")
    public List<Result> results = null;
    @JsonProperty("total_pages")
    public Long totalPages;
    @JsonProperty("total_results")
    public Long totalResults;

}
