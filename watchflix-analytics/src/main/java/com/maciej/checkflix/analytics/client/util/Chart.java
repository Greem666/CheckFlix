package com.maciej.checkflix.analytics.client.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Chart {
    @JsonProperty("type")
    private String type;
    @JsonProperty("data")
    private Data data;
}
