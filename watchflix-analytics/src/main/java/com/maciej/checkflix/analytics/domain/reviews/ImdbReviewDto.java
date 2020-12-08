package com.maciej.checkflix.analytics.domain.reviews;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImdbReviewDto {
    private int rating;
    private String title;
    private String username;
    private String date;
    private String review;
}
