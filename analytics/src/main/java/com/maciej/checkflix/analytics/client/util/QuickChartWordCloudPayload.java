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
        private int fontScale = 10;
        private String scale = "log";
        private List<String> colors;
        private int maxNumWords = 25;
        private boolean removeStopwords = true;
        private String language = "english";
        private int minWordLength = 2;
        private String text;

    public QuickChartWordCloudPayload(List<String> colors, String text) {
        this.colors = colors;
        this.text = text;
    }
}
