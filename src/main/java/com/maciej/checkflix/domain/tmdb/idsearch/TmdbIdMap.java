package com.maciej.checkflix.domain.tmdb.idsearch;

import com.maciej.checkflix.domain.omdb.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IMDBID_TMDBID_MAP")
public class TmdbIdMap {
    @Id
    @GeneratedValue
    @Column(name = "IMDB_TMDB_ID")
    private Long id;

    @Column(name = "IMDB_ID")
    private String imdbId;

    @Column(name = "TMDB_ID")
    private int tmdbId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TYPE")
    private Type type;

    public TmdbIdMap(String imdbId, int tmdbId, String title, Type type) {
        this.imdbId = imdbId;
        this.tmdbId = tmdbId;
        this.title = title;
        this.type = type;
    }
}
