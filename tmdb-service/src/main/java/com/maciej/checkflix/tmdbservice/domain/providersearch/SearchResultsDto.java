package com.maciej.checkflix.tmdbservice.domain.providersearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultsDto {
    @JsonProperty("AR")
    private CountryResultDto ar;

    @JsonProperty("AT")
    private CountryResultDto at;

    @JsonProperty("AU")
    private CountryResultDto au;

    @JsonProperty("BE")
    private CountryResultDto be;

    @JsonProperty("BR")
    private CountryResultDto br;

    @JsonProperty("CA")
    private CountryResultDto ca;

    @JsonProperty("CH")
    private CountryResultDto ch;

    @JsonProperty("CL")
    private CountryResultDto cl;

    @JsonProperty("CO")
    private CountryResultDto co;

    @JsonProperty("CZ")
    private CountryResultDto cz;

    @JsonProperty("DE")
    private CountryResultDto de;

    @JsonProperty("DK")
    private CountryResultDto dk;

    @JsonProperty("EC")
    private CountryResultDto ec;

    @JsonProperty("EE")
    private CountryResultDto ee;

    @JsonProperty("ES")
    private CountryResultDto es;

    @JsonProperty("FI")
    private CountryResultDto fi;

    @JsonProperty("FR")
    private CountryResultDto fr;

    @JsonProperty("GB")
    private CountryResultDto gb;

    @JsonProperty("GR")
    private CountryResultDto gr;

    @JsonProperty("HU")
    private CountryResultDto hu;

    @JsonProperty("ID")
    private CountryResultDto id;

    @JsonProperty("IE")
    private CountryResultDto ie;

    @JsonProperty("IN")
    private CountryResultDto in;

    @JsonProperty("IT")
    private CountryResultDto it;

    @JsonProperty("JP")
    private CountryResultDto jp;

    @JsonProperty("KR")
    private CountryResultDto kr;

    @JsonProperty("LT")
    private CountryResultDto lt;

    @JsonProperty("LV")
    private CountryResultDto lv;

    @JsonProperty("MX")
    private CountryResultDto mx;

    @JsonProperty("MY")
    private CountryResultDto my;

    @JsonProperty("NL")
    private CountryResultDto nl;

    @JsonProperty("NO")
    private CountryResultDto no;

    @JsonProperty("NZ")
    private CountryResultDto nz;

    @JsonProperty("PE")
    private CountryResultDto pe;

    @JsonProperty("PH")
    private CountryResultDto ph;

    @JsonProperty("PL")
    private CountryResultDto pl;

    @JsonProperty("PT")
    private CountryResultDto pt;

    @JsonProperty("RO")
    private CountryResultDto ro;

    @JsonProperty("RU")
    private CountryResultDto ru;

    @JsonProperty("SE")
    private CountryResultDto se;

    @JsonProperty("SG")
    private CountryResultDto sg;

    @JsonProperty("TH")
    private CountryResultDto th;

    @JsonProperty("TR")
    private CountryResultDto tr;

    @JsonProperty("US")
    private CountryResultDto us;

    @JsonProperty("VE")
    private CountryResultDto ve;

    @JsonProperty("ZA")
    private CountryResultDto za;
}
