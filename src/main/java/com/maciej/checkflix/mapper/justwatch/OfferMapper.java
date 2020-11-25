package com.maciej.checkflix.mapper.justwatch;

import com.maciej.checkflix.domain.justwatch.Offer;
import com.maciej.checkflix.domain.justwatch.OfferDto;
import com.maciej.checkflix.mapper.justwatch.util.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OfferMapper {

    @Autowired
    private UrlsMapper urlsMapper;

    public Offer mapToOffer(OfferDto offerDto) {
        return new Offer(
                offerDto.getMonetizationType(),
                offerDto.getProviderId(),
                offerDto.getCurrency(),
                offerDto.getUrlsDto() != null ? urlsMapper.mapToUrls(offerDto.getUrlsDto()) : null,
                offerDto.getPresentationType(),
                offerDto.getRetailPrice(),
                offerDto.getLastChangeRetailPrice(),
                offerDto.getLastChangeDifference(),
                offerDto.getLastChangePercent(),
                DateConverter.convertStringToLocalDate(offerDto.getLastChangeDate()),
                offerDto.getLastChangeDateProviderId()
                );
    }

    public OfferDto mapToOfferDto(Offer offer) {
        return new OfferDto(
                offer.getMonetizationType(),
                offer.getProviderId(),
                offer.getCurrency(),
                offer.getUrls() != null ? urlsMapper.mapToUrlsDto(offer.getUrls()) : null,
                offer.getPresentationType(),
                offer.getRetailPrice(),
                offer.getLastChangeRetailPrice(),
                offer.getLastChangeDifference(),
                offer.getLastChangePercent(),
                DateConverter.convertLocalDateToString(offer.getLastChangeDate()),
                offer.getLastChangeDateProviderId()
        );
    }

    public List<Offer> mapToOfferList(List<OfferDto> offerDtoList) {
        return offerDtoList.stream()
                .map(this::mapToOffer)
                .collect(Collectors.toList());
    }

    public List<OfferDto> mapToOfferDtoList(List<Offer> offerList) {
        return offerList.stream()
                .map(this::mapToOfferDto)
                .collect(Collectors.toList());
    }
}
