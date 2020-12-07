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
}