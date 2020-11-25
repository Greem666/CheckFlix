package com.maciej.checkflix.mapper.justwatch;

import com.maciej.checkflix.domain.justwatch.SearchResults;
import com.maciej.checkflix.domain.justwatch.SearchResultsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SearchResultsMapper {

    private final ItemMapper itemMapper;

    public SearchResults mapToSearchResults(SearchResultsDto searchResultsDto, String searchTitle, String searchLocale) {
        return new SearchResults(
                searchResultsDto.getTotalResults(),
                itemMapper.mapToItemList(searchResultsDto.getItemDtos()),
                LocalDateTime.now(),
                searchTitle,
                searchLocale
        );
    }

    public SearchResultsDto mapToSearchResultsDto(SearchResults searchResults) {
        return new SearchResultsDto(
                searchResults.getTotalResults(),
                itemMapper.mapToItemDtoList(searchResults.getItems())
        );
    }
}
