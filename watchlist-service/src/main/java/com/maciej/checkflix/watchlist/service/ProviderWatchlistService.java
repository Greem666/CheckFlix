package com.maciej.checkflix.watchlist.service;

import com.maciej.checkflix.watchlist.domain.ProviderWatchlist;
import com.maciej.checkflix.watchlist.domain.ProviderWatchlistDto;
import com.maciej.checkflix.watchlist.mapper.ProviderWatchlistMapper;
import com.maciej.checkflix.watchlist.repository.ProviderWatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProviderWatchlistService {
    private final ProviderWatchlistRepository providerWatchlistRepository;
    private final ProviderWatchlistMapper providerWatchlistMapper;

    public List<ProviderWatchlistDto> getAllItemsOnWatchlist() {
        return providerWatchlistMapper.mapToProviderWatchlistDtoList(providerWatchlistRepository.findAll());
    }

    public ProviderWatchlistDto getItemOnWatchlist(Long itemId) {
        Optional<ProviderWatchlist> item = providerWatchlistRepository.findById(itemId);
        return providerWatchlistMapper.mapToProviderWatchlistDto(item.orElse(new ProviderWatchlist()));
    }

    public ProviderWatchlistDto addNewItemToWatchlist(ProviderWatchlistDto providerWatchlistDto) {
        Optional<ProviderWatchlist> previousEntry = providerWatchlistRepository.findByEmailAndImdbId(
                providerWatchlistDto.getEmail(),
                providerWatchlistDto.getImdbId()
        );
        if (!previousEntry.isPresent()) {
            return providerWatchlistMapper.mapToProviderWatchlistDto(
                    providerWatchlistRepository.save(
                            providerWatchlistMapper.mapToProviderWatchlist(providerWatchlistDto)
                    )
            );
        }
        return new ProviderWatchlistDto();
    }

    public ProviderWatchlistDto modifyItemOnWatchlist(ProviderWatchlistDto providerWatchlistDto) {
        Optional<ProviderWatchlist> previousEntry = providerWatchlistRepository.findById(
                providerWatchlistDto.getId()
        );
        if (previousEntry.isPresent()) {
            return providerWatchlistMapper.mapToProviderWatchlistDto(
                    providerWatchlistRepository.save(
                            providerWatchlistMapper.mapToProviderWatchlist(providerWatchlistDto)
                    )
            );
        }
        return new ProviderWatchlistDto();
    }

    public void removeItemFromWatchlist(Long itemId) {
        Optional<ProviderWatchlist> previousEntry = providerWatchlistRepository.findById(itemId);
        if (previousEntry.isPresent()) {
            providerWatchlistRepository.deleteById(itemId);
        }
    }

    public List<ProviderWatchlistDto> getItemOnProviderWatchlistBy(String phrase) {
        List<ProviderWatchlist> searchResults = providerWatchlistRepository.findBySearchPhrase(phrase);
        return providerWatchlistMapper.mapToProviderWatchlistDtoList(searchResults);
    }
}
