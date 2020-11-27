package com.maciej.checkflix.mapper.justwatch;

import com.maciej.checkflix.domain.justwatch.*;
import com.maciej.checkflix.mapper.justwatch.util.DateConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private final OfferMapper offerMapper;
    private final ScoringMapper scoringMapper;

    public Item mapToItem(ItemDto itemDto) {
        return new Item(
                itemDto.getJwEntityId(),
                itemDto.getId(),
                itemDto.getTitle(),
                itemDto.getFullPath(),
                itemDto.getFullPathsDto().getMovieDetailOverview(),
                itemDto.getPoster(),
                itemDto.getOriginalReleaseYear(),
                itemDto.getTmdbPopularity(),
                itemDto.getObjectType(),
                DateConverter.convertStringToLocalDate(itemDto.getLocalizedReleaseDate()),
                itemDto.getOfferDtos() != null ? new HashSet<>(offerMapper.mapToOfferList(itemDto.getOfferDtos())) : new HashSet<>(),
                itemDto.getScoringDto() != null ? new HashSet<>(scoringMapper.mapToScoringList(itemDto.getScoringDto())) : new HashSet<>(),
                DateConverter.convertStringToLocalDate(itemDto.getCinemaReleaseDate())
        );
    }

    public ItemDto mapToItemDto(Item item) {
        return new ItemDto(
                item.getJwEntityId(),
                item.getId(),
                item.getTitle(),
                item.getFullPath(),
                new FullPathsDto(item.getFullPaths()),
                item.getPoster(),
                item.getOriginalReleaseYear(),
                item.getTmdbPopularity(),
                item.getObjectType(),
                DateConverter.convertLocalDateToString(item.getLocalizedReleaseDate()),
                item.getOffers() != null ? offerMapper.mapToOfferDtoList(new ArrayList<>(item.getOffers())) : null,
                item.getScoring() != null ? scoringMapper.mapToScoringDtoList(new ArrayList<>(item.getScoring())): null,
                DateConverter.convertLocalDateToString(item.getCinemaReleaseDate())
        );
    }

    public List<Item> mapToItemList(List<ItemDto> itemDtoList) {
        return itemDtoList.stream()
                .map(this::mapToItem)
                .collect(Collectors.toList());
    }

    public List<ItemDto> mapToItemDtoList(List<Item> itemList) {
        return itemList.stream()
                .map(this::mapToItemDto)
                .collect(Collectors.toList());
    }
}
