package com.maciej.checkflix.analytics.client.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuickChartPayload {
    @JsonProperty("backgroundColor")
    private String backgroundColor = "transparent";
    @JsonProperty("width")
    private int width = 600;
    @JsonProperty("height")
    private int height = 600;
    @JsonProperty("format")
    private String format = "png";
    @JsonProperty("chart")
    private Chart chart;
}



