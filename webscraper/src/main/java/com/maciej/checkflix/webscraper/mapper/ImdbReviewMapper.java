package com.maciej.checkflix.webscraper.mapper;

import com.maciej.checkflix.webscraper.domain.ImdbReview;
import com.maciej.checkflix.webscraper.domain.ImdbReviewDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImdbReviewMapper {

    public ImdbReview mapToImdbReview(ImdbReviewDto imdbReviewDto, String imdbId) {
        String[] dateSplit = imdbReviewDto.getDate().split(" ");
        return new ImdbReview(
                LocalDateTime.now(ZoneId.of("UTC")),
                imdbId,
                imdbReviewDto.getRating(),
                imdbReviewDto.getTitle(),
                imdbReviewDto.getUsername(),
                LocalDate.of(
                        Integer.parseInt(dateSplit[2]),
                        Month.valueOf(dateSplit[1].toUpperCase()),
                        Integer.parseInt(dateSplit[0])),
                imdbReviewDto.getReview()
        );
    }

    public ImdbReviewDto mapToImdbReviewDto(ImdbReview imdbReview) {
        return new ImdbReviewDto(
                imdbReview.getRating(),
                imdbReview.getTitle(),
                imdbReview.getUsername(),
                imdbReview.getDate().toString(),
                imdbReview.getReview()
        );
    }

    public List<ImdbReview> mapToImdbReviewList(List<ImdbReviewDto> imdbReviewDtoList, String imdbId) {
        return imdbReviewDtoList.stream()
                .map(e -> this.mapToImdbReview(e, imdbId))
                .collect(Collectors.toList());
    }

    public List<ImdbReviewDto> mapToImdbReviewDtoList(List<ImdbReview> imdbReviewList) {
        return imdbReviewList.stream()
                .map(this::mapToImdbReviewDto)
                .collect(Collectors.toList());
    }
}
