package com.maciej.checkflix.webscraper.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "IMDB_REVIEWS")
public class ImdbReview {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "SCRAPED_ON")
    private LocalDateTime scrapedOn;

    @Column(name = "IMDB_ID")
    private String imdbId;

    @EqualsAndHashCode.Include
    @Column(name = "RATING")
    private int rating;

    @EqualsAndHashCode.Include
    @Column(name = "TITLE")
    private String title;

    @EqualsAndHashCode.Include
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "DATE")
    private LocalDate date;

    @Lob
    @Column(name = "REVIEW")
    private String review;

    public ImdbReview(LocalDateTime scrapedOn, String imdbId, int rating, String title,
                      String username, LocalDate date, String review) {
        this.scrapedOn = scrapedOn;
        this.imdbId = imdbId;
        this.rating = rating;
        this.title = title;
        this.username = username;
        this.date = date;
        this.review = review;
    }
}
