package com.maciej.checkflix.domain.justwatch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SCORING")
public class Scoring {
    @Id
    @GeneratedValue
    @Column(name = "SCORING_ID")
    private Long scoringId;

    @Column(name = "PROVIDER_TYPE")
    public String providerType;

    @Column(name = "VALUE")
    public Double value;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    public Item item;

    public Scoring(String providerType, Double value) {
        this.providerType = providerType;
        this.value = value;
    }

    public void addItem(Item item) {
        this.item = item;
        item.getScoring().add(this);
    }
}
