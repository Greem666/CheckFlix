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
@Table(name = "URLS")
public class Urls {
    @Id
    @GeneratedValue
    @Column(name = "URLS_ID")
    private Long urlsId;

    @Column(name = "STANDARD_WEB", length = 3000)
    public String standardWeb;

    @Column(name = "DEEPLINK_ANDROID_TV")
    public String deeplinkAndroidTv;

    @Column(name = "DEEPLINK_FIRE_TV")
    public String deeplinkFireTv;

    @Column(name = "DEEPLINK_TVOS")
    public String deeplinkTvos;

    @OneToOne(mappedBy = "urls")
    @JoinColumn(name = "OFFER_ID", referencedColumnName = "OFFER_ID")
    private Offer offer;

    public Urls(String standardWeb, String deeplinkAndroidTv, String deeplinkFireTv, String deeplinkTvos) {
        this.standardWeb = standardWeb;
        this.deeplinkAndroidTv = deeplinkAndroidTv;
        this.deeplinkFireTv = deeplinkFireTv;
        this.deeplinkTvos = deeplinkTvos;
    }

    public void addOffer(Offer offer) {
        this.offer = offer;
        offer.setUrls(this);
    }
}
