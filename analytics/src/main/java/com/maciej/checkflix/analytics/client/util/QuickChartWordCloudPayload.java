package com.maciej.checkflix.analytics.client.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sun.net.www.content.image.png;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuickChartWordCloudPayload {
        private String format = "png";
        private int width = 600;
        private int height = 600;
        private int fontScale = 5;
        private String scale = "linear";
        private List<String> colors = Arrays.asList("rgba(17, 164, 14, 1)", "rgba(21, 202, 18, 1)", "rgba(107, 242, 105, 1)");
        private int maxNumWords = 10;
        private boolean removeStopwords = true;
        private int minWordLength = 4;
        private String text;

    public QuickChartWordCloudPayload(String text) {
        this.text = text;
    }
}
