package com.maciej.checkflix.tmdbservice.service.util;

import com.maciej.checkflix.tmdbservice.domain.providersearch.CountryResultDto;
import com.maciej.checkflix.tmdbservice.domain.providersearch.SearchResultsDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountrySpecificProviderFactoryTestSuite {

    @Autowired
    private CountrySpecificProviderFactory countrySpecificProviderFactory;

    @Test
    public void findLocalProvideCorrectCountry() {
        // Given
        CountryResultDto countryResultDto = new CountryResultDto(
                "testLink", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        SearchResultsDto searchResultsDto = new SearchResultsDto(
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, countryResultDto, null, null, null, null,
                null, null, null, null, null, null);

        // When
        CountryResultDto result = countrySpecificProviderFactory.findLocalProvider(searchResultsDto, "Poland");

        // Then
        Assert.assertEquals(countryResultDto.getLink(), result.getLink());
        Assert.assertEquals(0, result.getFlatrate().size());
        Assert.assertEquals(0, result.getRent().size());
        Assert.assertEquals(0, result.getBuy().size());
    }

    @Test
    public void findLocalProviderIncorrectCountry() {
        // Given
        CountryResultDto countryResultDto = new CountryResultDto(
                "testLink", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        SearchResultsDto searchResultsDto = new SearchResultsDto(
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, countryResultDto, null, null, null, null,
                null, null, null, null, null, null);

        // When
        CountryResultDto result = countrySpecificProviderFactory.findLocalProvider(searchResultsDto, "incorrect-country-name");

        // Then
        Assert.assertEquals(countryResultDto.getLink(), result.getLink());
        Assert.assertEquals(0, result.getFlatrate().size());
        Assert.assertEquals(0, result.getRent().size());
        Assert.assertEquals(0, result.getBuy().size());
    }

    @Test
    public void findLocalProvideCorrectCountryLoop() {
        // Given
        SearchResultsDto searchResultsDto = new SearchResultsDto(
                new CountryResultDto("AR", null, null, null),
                new CountryResultDto("AT", null, null, null),
                new CountryResultDto("AU", null, null, null),
                new CountryResultDto("BE", null, null, null),
                new CountryResultDto("BR", null, null, null),
                new CountryResultDto("CA", null, null, null),
                new CountryResultDto("CH", null, null, null),
                new CountryResultDto("CL", null, null, null),
                new CountryResultDto("CO", null, null, null),
                new CountryResultDto("CZ", null, null, null),
                new CountryResultDto("DE", null, null, null),
                new CountryResultDto("DK", null, null, null),
                new CountryResultDto("EC", null, null, null),
                new CountryResultDto("EE", null, null, null),
                new CountryResultDto("ES", null, null, null),
                new CountryResultDto("FI", null, null, null),
                new CountryResultDto("FR", null, null, null),
                new CountryResultDto("GB", null, null, null),
                new CountryResultDto("GR", null, null, null),
                new CountryResultDto("HU", null, null, null),
                new CountryResultDto("ID", null, null, null),
                new CountryResultDto("IE", null, null, null),
                new CountryResultDto("IN", null, null, null),
                new CountryResultDto("IT", null, null, null),
                new CountryResultDto("JP", null, null, null),
                new CountryResultDto("KR", null, null, null),
                new CountryResultDto("LT", null, null, null),
                new CountryResultDto("LV", null, null, null),
                new CountryResultDto("MX", null, null, null),
                new CountryResultDto("MY", null, null, null),
                new CountryResultDto("NL", null, null, null),
                new CountryResultDto("NO", null, null, null),
                new CountryResultDto("NZ", null, null, null),
                new CountryResultDto("PE", null, null, null),
                new CountryResultDto("PH", null, null, null),
                new CountryResultDto("PL", null, null, null),
                new CountryResultDto("PT", null, null, null),
                new CountryResultDto("RO", null, null, null),
                new CountryResultDto("RU", null, null, null),
                new CountryResultDto("SE", null, null, null),
                new CountryResultDto("SG", null, null, null),
                new CountryResultDto("TH", null, null, null),
                new CountryResultDto("TR", null, null, null),
                new CountryResultDto("US", null, null, null),
                new CountryResultDto("VE", null, null, null),
                new CountryResultDto("ZA", null, null, null));

        List<String> availableCountries = Arrays.stream(
                SupportedCountries.values()).map(SupportedCountries::getCountryName).collect(Collectors.toList());

        for (String countryName: availableCountries) {
            // When
            CountryResultDto result = countrySpecificProviderFactory.findLocalProvider(searchResultsDto, countryName);

            // Then
            SupportedCountries expectedCountry = !countryName.equals("default") ?
                    SupportedCountries.from(countryName) : SupportedCountries.POLAND;
            SupportedCountries returnedCountry = SupportedCountries.from(result.getLink());
            Assert.assertEquals(expectedCountry, returnedCountry);
        }
    }
}