package com.maciej.checkflix.analytics.client.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Data {
    @JsonProperty("labels")
    private List<String> labels;
    @JsonProperty("datasets")
    private List<Datasets> datasets;
}
