package com.maciej.checkflix.mapper.justwatch;

import com.maciej.checkflix.domain.justwatch.Urls;
import com.maciej.checkflix.domain.justwatch.UrlsDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UrlsMapper {

    public Urls mapToUrls(UrlsDto urlsDto) {
        return new Urls(
                urlsDto.getStandardWeb(),
                urlsDto.getDeeplinkAndroidTv(),
                urlsDto.getDeeplinkFireTv(),
                urlsDto.getDeeplinkTvos()
        );
    }

    public UrlsDto mapToUrlsDto(Urls urls) {
        return new UrlsDto(
                urls.getStandardWeb(),
                urls.getDeeplinkAndroidTv(),
                urls.getDeeplinkFireTv(),
                urls.getDeeplinkTvos()
        );
    }
}
