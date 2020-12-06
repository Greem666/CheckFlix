package com.maciej.checkflix.analytics.client.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Datasets {
    @JsonProperty("data")
    private List<Integer> data;
    @JsonProperty("label")
    private String label;
    @JsonProperty("backgroundColor")
    private List<String> backgroundColor;
}
