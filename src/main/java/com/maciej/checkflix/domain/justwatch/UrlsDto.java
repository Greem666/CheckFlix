package com.maciej.checkflix.domain.justwatch;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlsDto {
    @JsonProperty("standard_web")
    public String standardWeb;

    @JsonProperty("deeplink_android_tv")
    public String deeplinkAndroidTv;

    @JsonProperty("deeplink_fire_tv")
    public String deeplinkFireTv;

    @JsonProperty("deeplink_tvos")
    public String deeplinkTvos;
}
