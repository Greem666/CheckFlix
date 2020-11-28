package com.maciej.checkflix.domain.tmdb.providersearch;

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
    private CountryResultDto Ar;

    @JsonProperty("AT")
    private CountryResultDto At;

    @JsonProperty("AU")
    private CountryResultDto Au;

    @JsonProperty("BE")
    private CountryResultDto Be;

    @JsonProperty("BR")
    private CountryResultDto Br;

    @JsonProperty("CA")
    private CountryResultDto Ca;

    @JsonProperty("CH")
    private CountryResultDto Ch;

    @JsonProperty("CL")
    private CountryResultDto Cl;

    @JsonProperty("CO")
    private CountryResultDto Co;

    @JsonProperty("CZ")
    private CountryResultDto Cz;

    @JsonProperty("DE")
    private CountryResultDto De;

    @JsonProperty("DK")
    private CountryResultDto Dk;

    @JsonProperty("EC")
    private CountryResultDto Ec;

    @JsonProperty("EE")
    private CountryResultDto Ee;

    @JsonProperty("ES")
    private CountryResultDto Es;

    @JsonProperty("FI")
    private CountryResultDto Fi;

    @JsonProperty("FR")
    private CountryResultDto Fr;

    @JsonProperty("GB")
    private CountryResultDto Gb;

    @JsonProperty("GR")
    private CountryResultDto Gr;

    @JsonProperty("HU")
    private CountryResultDto Hu;

    @JsonProperty("ID")
    private CountryResultDto Id;

    @JsonProperty("IE")
    private CountryResultDto Ie;

    @JsonProperty("IN")
    private CountryResultDto In;

    @JsonProperty("IT")
    private CountryResultDto It;

    @JsonProperty("JP")
    private CountryResultDto Jp;

    @JsonProperty("KR")
    private CountryResultDto Kr;

    @JsonProperty("LT")
    private CountryResultDto Lt;

    @JsonProperty("LV")
    private CountryResultDto Lv;

    @JsonProperty("MX")
    private CountryResultDto Mx;

    @JsonProperty("MY")
    private CountryResultDto My;

    @JsonProperty("NL")
    private CountryResultDto Nl;

    @JsonProperty("NO")
    private CountryResultDto No;

    @JsonProperty("NZ")
    private CountryResultDto Nz;

    @JsonProperty("PE")
    private CountryResultDto Pe;

    @JsonProperty("PH")
    private CountryResultDto Ph;

    @JsonProperty("PL")
    private CountryResultDto Pl;

    @JsonProperty("PT")
    private CountryResultDto Pt;

    @JsonProperty("RO")
    private CountryResultDto Ro;

    @JsonProperty("RU")
    private CountryResultDto Ru;

    @JsonProperty("SE")
    private CountryResultDto Se;

    @JsonProperty("SG")
    private CountryResultDto Sg;

    @JsonProperty("TH")
    private CountryResultDto Th;

    @JsonProperty("TR")
    private CountryResultDto Tr;

    @JsonProperty("US")
    private CountryResultDto Us;

    @JsonProperty("VE")
    private CountryResultDto Ve;

    @JsonProperty("ZA")
    private CountryResultDto Za;
}
