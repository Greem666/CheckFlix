package com.maciej.checkflix.domain.justwatch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ITEM")
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long itemId;

    @Column(name = "JW_ENTITY_ID")
    public String jwEntityId;

    @Column(name = "ID")
    public Long id;

    @Column(name = "TITLE")
    public String title;

    @Column(name = "FULL_PATH")
    public String fullPath;

    @Column(name = "FULL_PATHS")
    public String fullPaths;

    @Column(name = "POSTER")
    public String poster;

    @Column(name = "ORIGINAL_RELEASE_YEAR")
    public Long originalReleaseYear;

    @Column(name = "TMDB_POPULARITY")
    public Double tmdbPopularity;

    @Column(name = "OBJECT_TYPE")
    public String objectType;

    @Column(name = "LOCALIZED_RELEASE_DATE")
    public LocalDate localizedReleaseDate;

    @OneToMany(
            targetEntity = Offer.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "ITEM_ID")
    public Set<Offer> offers = new HashSet<>();

    @OneToMany(
            targetEntity = Scoring.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "ITEM_ID")
    public Set<Scoring> scoring = new HashSet<>();

    @Column(name = "CINEMA_RELEASE_DATE")
    public LocalDate cinemaReleaseDate;

    @ManyToOne
    @JoinColumn(name = "SEARCH_RESULTS_ID", referencedColumnName = "SEARCH_RESULTS_ID")
    public SearchResults searchResults;

    public Item(String jwEntityId, Long id, String title, String fullPath, String fullPaths, String poster,
                Long originalReleaseYear, Double tmdbPopularity, String objectType, LocalDate localizedReleaseDate,
                Set<Offer> offers, Set<Scoring> scoring, LocalDate cinemaReleaseDate) {
        this.jwEntityId = jwEntityId;
        this.id = id;
        this.title = title;
        this.fullPath = fullPath;
        this.fullPaths = fullPaths;
        this.poster = poster;
        this.originalReleaseYear = originalReleaseYear;
        this.tmdbPopularity = tmdbPopularity;
        this.objectType = objectType;
        this.localizedReleaseDate = localizedReleaseDate;
        this.offers = offers;
        this.scoring = scoring;
        this.cinemaReleaseDate = cinemaReleaseDate;
    }

    public void addOffer(Offer offer) {
        this.offers.add(offer);
        offer.setItem(this);
    }

    public void addScoring(Scoring scoring) {
        this.scoring.add(scoring);
        scoring.setItem(this);
    }

    public void addSearchResults(SearchResults searchResults) {
        this.searchResults = searchResults;
        searchResults.getItems().add(this);
    }
}
