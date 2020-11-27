package com.maciej.checkflix.domain.justwatch;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferDto {
    @JsonProperty("monetization_type")
    public String monetizationType;

    @JsonProperty("provider_id")
    public Long providerId;

    @JsonProperty("currency")
    public String currency;

    @JsonProperty("urls")
    public UrlsDto urlsDto;

    @JsonProperty("presentation_type")
    public String presentationType;

    @JsonProperty("retail_price")
    public Double retailPrice;

    @JsonProperty("last_change_retail_price")
    public Double lastChangeRetailPrice;

    @JsonProperty("last_change_difference")
    public Long lastChangeDifference;

    @JsonProperty("last_change_percent")
    public Double lastChangePercent;

    @JsonProperty("last_change_date")
    public String lastChangeDate;

    @JsonProperty("last_change_date_provider_id")
    public String lastChangeDateProviderId;

    @Override
    public String toString() {
        return "OfferDto{" +
                "monetizationType='" + monetizationType + '\'' +
                ", providerId=" + providerId +
                ", currency='" + currency + '\'' +
                ", urlsDto=" + urlsDto +
                ", presentationType='" + presentationType + '\'' +
                ", retailPrice=" + retailPrice +
                ", lastChangeRetailPrice=" + lastChangeRetailPrice +
                ", lastChangeDifference=" + lastChangeDifference +
                ", lastChangePercent=" + lastChangePercent +
                ", lastChangeDate='" + lastChangeDate + '\'' +
                ", lastChangeDateProviderId='" + lastChangeDateProviderId + '\'' +
                '}';
    }
}
