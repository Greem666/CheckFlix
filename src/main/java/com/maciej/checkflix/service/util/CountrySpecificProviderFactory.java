package com.maciej.checkflix.service.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maciej.checkflix.clients.TmdbClient;
import com.maciej.checkflix.domain.omdb.Type;
import com.maciej.checkflix.domain.tmdb.providersearch.CountryResultDto;
import com.maciej.checkflix.domain.tmdb.providersearch.SearchResultsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CountrySpecificProviderFactory {
    @Autowired
    private TmdbClient tmdbClient;

    public final CountryResultDto findLocalProvider(final SearchResultsDto allResults, final String countryCode) {
        SupportedCountries country = Optional.ofNullable(
                SupportedCountries.from(countryCode)).orElse(SupportedCountries.DEFAULT);
        CountryResultDto countryResult;
        switch (country) {
            case ARGENTINA:
                countryResult = allResults.getAr();
                break;
            case AUSTRIA:
                countryResult = allResults.getAt();
                break;
            case AUSTRALIA:
                countryResult = allResults.getAu();
                break;
            case BELGIUM:
                countryResult = allResults.getBe();
                break;
            case BRAZIL:
                countryResult = allResults.getBr();
                break;
            case CANADA:
                countryResult = allResults.getCa();
                break;
            case SWITZERLAND:
                countryResult = allResults.getCh();
                break;
            case CHILE:
                countryResult = allResults.getCl();
                break;
            case COLOMBIA:
                countryResult = allResults.getCo();
                break;
            case CZECHIA:
                countryResult = allResults.getCz();
                break;
            case GERMANY:
                countryResult = allResults.getDe();
                break;
            case DENMARK:
                countryResult = allResults.getDk();
                break;
            case ECUADOR:
                countryResult = allResults.getEc();
                break;
            case ESTONIA:
                countryResult = allResults.getEe();
                break;
            case SPAIN:
                countryResult = allResults.getEs();
                break;
            case FINLAND:
                countryResult = allResults.getFi();
                break;
            case FRANCE:
                countryResult = allResults.getFr();
                break;
            case GREAT_BRITAIN:
                countryResult = allResults.getGb();
                break;
            case GREECE:
                countryResult = allResults.getGr();
                break;
            case HUNGARY:
                countryResult = allResults.getHu();
                break;
            case INDONESIA:
                countryResult = allResults.getId();
                break;
            case IRELAND:
                countryResult = allResults.getIe();
                break;
            case INDIA:
                countryResult = allResults.getIn();
                break;
            case ITALY:
                countryResult = allResults.getIt();
                break;
            case JAPAN:
                countryResult = allResults.getJp();
                break;
            case REPUBLIC_OF_KOREA:
                countryResult = allResults.getKr();
                break;
            case LITHUANIA:
                countryResult = allResults.getLt();
                break;
            case LATVIA:
                countryResult = allResults.getLv();
                break;
            case MEXICO:
                countryResult = allResults.getMx();
                break;
            case MALAYSIA:
                countryResult = allResults.getMy();
                break;
            case NETHERLANDS:
                countryResult = allResults.getNl();
                break;
            case NORWAY:
                countryResult = allResults.getNo();
                break;
            case NEW_ZEALAND:
                countryResult = allResults.getNz();
                break;
            case PERU:
                countryResult = allResults.getPe();
                break;
            case PHILIPPINES:
                countryResult = allResults.getPh();
                break;
            case POLAND:
                countryResult = allResults.getPl();
                break;
            case PORTUGAL:
                countryResult = allResults.getPt();
                break;
            case ROMANIA:
                countryResult = allResults.getRo();
                break;
            case RUSSIA:
                countryResult = allResults.getRu();
                break;
            case SWEDEN:
                countryResult = allResults.getSe();
                break;
            case SINGAPORE:
                countryResult = allResults.getSg();
                break;
            case THAILAND:
                countryResult = allResults.getTh();
                break;
            case TURKEY:
                countryResult = allResults.getTr();
                break;
            case UNITED_STATES:
                countryResult = allResults.getUs();
                break;
            case VENEZUELA:
                countryResult = allResults.getVe();
                break;
            case SOUTH_AFRICA:
                countryResult = allResults.getZa();
                break;
            default:
                countryResult = allResults.getPl();
                break;
        }

        return Optional.ofNullable(countryResult).orElse(new CountryResultDto());
    }
}


