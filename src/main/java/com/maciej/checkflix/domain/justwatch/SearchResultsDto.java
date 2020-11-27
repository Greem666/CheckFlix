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
public class SearchResultsDto {
    @JsonProperty("total_results")
    public Long totalResults;

    @JsonProperty("items")
    public List<ItemDto> itemDtos = null;
}
