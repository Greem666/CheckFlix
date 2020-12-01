package com.maciej.checkflix.service.util;

import lombok.Getter;

@Getter
public enum SupportedCountries {
    ARGENTINA("AR", "ARG", "Argentina"),
    AUSTRIA("AT", "AUT", "Austria"),
    AUSTRALIA("AU", "AUS", "Australia"),
    BELGIUM("BE", "BEL", "Belgium"),
    BRAZIL("BR", "BRA", "Brazil"),
    CANADA("CA", "CAN", "Canada"),
    SWITZERLAND("CH", "CHE", "Switzerland"),
    CHILE("CL", "CHL", "Chile"),
    COLOMBIA("CO", "COL", "Colombia"),
    CZECHIA("CZ", "CZE", "Czechia"),
    GERMANY("DE", "DEU", "Germany"),
    DENMARK("DK", "DNK", "Denmark"),
    ECUADOR("EC", "ECU", "Ecuador"),
    ESTONIA("EE", "EST", "Estonia"),
    SPAIN("ES", "ESP", "Spain"),
    FINLAND("FI", "FIN", "Finland"),
    FRANCE("FR", "FRA", "France"),
    GREAT_BRITAIN("GB", "GBR", "Great Britain"),
    GREECE("GR", "GRC", "Greece"),
    HUNGARY("HU", "HUN", "Hungary"),
    INDONESIA("ID", "IDN", "Indonesia"),
    IRELAND("IE", "IRL", "Ireland"),
    INDIA("IN", "IND", "India"),
    ITALY("IT", "ITA", "Italy"),
    JAPAN("JP", "JPN", "Japan"),
    REPUBLIC_OF_KOREA("KR", "KOR", "Korea"),
    LITHUANIA("LT", "LTU", "Lithuania"),
    LATVIA("LV", "LVA", "Latvia"),
    MEXICO("MX", "MEX", "Mexico"),
    MALAYSIA("MY", "MYS", "Malaysia"),
    NETHERLANDS("NL", "NLD", "Netherlands"),
    NORWAY("NO", "NOR", "Norway"),
    NEW_ZEALAND("NZ", "NZL", "New Zealand"),
    PERU("PE", "PER", "Peru"),
    PHILIPPINES("PH", "PHL", "Philippines"),
    POLAND("PL", "POL", "Poland"),
    PORTUGAL("PT", "PRT", "Portugal"),
    ROMANIA("RO", "ROU", "Romania"),
    RUSSIA("RU", "RUS", "Russia"),
    SWEDEN("SE", "SWE", "Sweden"),
    SINGAPORE("SG", "SGP", "Singapore"),
    THAILAND("TH", "THA", "Thailand"),
    TURKEY("TR", "TUR", "Turkey"),
    UNITED_STATES("US", "USA", "United States"),
    VENEZUELA("VE", "VEN", "Venezuela"),
    SOUTH_AFRICA("ZA", "ZAF", "South Africa"),
    DEFAULT("deafult", "default", "default");

    private String alpha2code;
    private String alpha3code;
    private String countryName;

    SupportedCountries(String alpha2code, String alpha3code, String countryName) {
        this.alpha2code = alpha2code;
        this.alpha3code = alpha3code;
        this.countryName = countryName;
    }

    public static SupportedCountries from(String text) {
        for (SupportedCountries code : SupportedCountries.values()) {
            if (
                    code.alpha2code.equalsIgnoreCase(text) ||
                    code.alpha3code.equalsIgnoreCase(text) ||
                    code.countryName.equalsIgnoreCase(text)
            ) {
                return code;
            }
        }
        return null;
    }

}
