package com.maciej.checkflix.scheduler;

import com.maciej.checkflix.repository.watchlist.ProviderWatchlistRepository;
import com.maciej.checkflix.service.WatchlistEmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProviderWatchlistScheduler {
    private static final Logger logger = LoggerFactory.getLogger(ProviderWatchlistScheduler.class);

    private final WatchlistEmailService watchlistEmailService;
    private final ProviderWatchlistRepository providerWatchlistRepository;

    @Scheduled(cron = "0 */12 * * *")
    public void notifyAboutProviderAvailabilityChange() {
        // logic for this...
    }
}
