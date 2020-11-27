package com.maciej.checkflix.domain.justwatch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OFFER")
public class Offer {
    @Id
    @GeneratedValue
    @Column(name = "OFFER_ID")
    private Long offerId;

    @Column(name = "MONETIZATION_TYPE")
    public String monetizationType;

    @Column(name = "PROVIDER_ID")
    public Long providerId;

    @Column(name = "CURRENCY")
    public String currency;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "URLS_ID", referencedColumnName = "URLS_ID")
    public Urls urls;

    @Column(name = "PRESENTATION_TYPE")
    public String presentationType;

    @Column(name = "RETAIL_PRICE")
    public Double retailPrice;

    @Column(name = "LAST_CHANGE_RETAIL_PRICE")
    public Double lastChangeRetailPrice;

    @Column(name = "LAST_CHANGE_DIFFERENCE")
    public Long lastChangeDifference;

    @Column(name = "LAST_CHANGE_PERCENT")
    public Double lastChangePercent;

    @Column(name = "LAST_CHANGE_DATE")
    public LocalDate lastChangeDate;

    @Column(name = "LAST_CHANGE_DATE_PROVIDER_ID")
    public String lastChangeDateProviderId;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    public Item item;

    public Offer(String monetizationType, Long providerId, String currency, Urls urls, String presentationType,
                 Double retailPrice, Double lastChangeRetailPrice, Long lastChangeDifference, Double lastChangePercent,
                 LocalDate lastChangeDate, String lastChangeDateProviderId) {
        this.monetizationType = monetizationType;
        this.providerId = providerId;
        this.currency = currency;
        this.urls = urls;
        this.presentationType = presentationType;
        this.retailPrice = retailPrice;
        this.lastChangeRetailPrice = lastChangeRetailPrice;
        this.lastChangeDifference = lastChangeDifference;
        this.lastChangePercent = lastChangePercent;
        this.lastChangeDate = lastChangeDate;
        this.lastChangeDateProviderId = lastChangeDateProviderId;
    }

    public void addUrls(Urls urls) {
        this.urls = urls;
        urls.setOffer(this);
    }
}
