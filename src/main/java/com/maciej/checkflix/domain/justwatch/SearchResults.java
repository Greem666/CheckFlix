package com.maciej.checkflix.domain.justwatch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SEARCH_RESULTS")
public class SearchResults {
    @Id
    @GeneratedValue
    @Column(name = "SEARCH_RESULTS_ID")
    private Long searchResultsId;

    @Column(name = "TOTAL_RESULTS")
    public Long totalResults;

    @OneToMany(
            targetEntity = Item.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "SEARCH_RESULTS_ID")
    public List<Item> items = new ArrayList<>();

    @NotNull
    @Column(name = "SEARCH_DATE")
    private LocalDateTime searchOn;

    @NotNull
    @Column(name = "SEARCH_TITLE")
    private String searchTitle;

    @NotNull
    @Column(name = "SEARCH_LOCALE")
    private String searchLocale;

    public SearchResults(Long totalResults, List<Item> items,
                         @NotNull LocalDateTime searchOn, @NotNull String searchTitle,
                         @NotNull String searchLocale) {
        this.totalResults = totalResults;
        this.items = items;
        this.searchOn = searchOn;
        this.searchTitle = searchTitle;
        this.searchLocale = searchLocale;
    }

    public void addItem(Item item) {
        this.items.add(item);
        item.setSearchResults(this);
    }
}
