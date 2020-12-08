package com.maciej.checkflix.webscraper.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ImdbReviewDto {
    @EqualsAndHashCode.Include private int rating;
    @EqualsAndHashCode.Include private String title;
    @EqualsAndHashCode.Include private String username;
    private String date;
    private String review;
}
