package com.maciej.checkflix.watchlist.controller;

import com.maciej.checkflix.watchlist.domain.ProviderWatchlistDto;
import com.maciej.checkflix.watchlist.service.ProviderWatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class ProviderWatchlistController {

    @Autowired
    private ProviderWatchlistService providerWatchlistService;

    @RequestMapping(method = RequestMethod.GET, value = "/watchlist")
    public List<ProviderWatchlistDto> getAllItemsOnProviderWatchlist() {
        return providerWatchlistService.getAllItemsOnWatchlist();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/watchlist/{itemId}")
    public ProviderWatchlistDto getItemOnProviderWatchlist(@PathVariable Long itemId) {
        return providerWatchlistService.getItemOnWatchlist(itemId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/watchlist/search")
    public List<ProviderWatchlistDto> getItemOnProviderWatchlistBy(@RequestParam String phrase) {
        return providerWatchlistService.getItemOnProviderWatchlistBy(phrase);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/watchlist")
    public ProviderWatchlistDto addItemToProviderWatchlist(@RequestBody ProviderWatchlistDto providerWatchlistDto) {
        return providerWatchlistService.addNewItemToWatchlist(providerWatchlistDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/watchlist")
    public ProviderWatchlistDto modifyItemOnProviderWatchlist(@RequestBody ProviderWatchlistDto providerWatchlistDto) {
        return providerWatchlistService.modifyItemOnWatchlist(providerWatchlistDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/watchlist/{itemId}")
    public void removeItemFromProviderWatchlist(@PathVariable Long itemId) {
        providerWatchlistService.removeItemFromWatchlist(itemId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/watchlist/types")
    public List<String> getProviderTypes() {
        return providerWatchlistService.getProviderTypes();
    }
}
