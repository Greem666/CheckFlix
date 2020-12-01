package com.maciej.checkflix.scheduler;

import com.maciej.checkflix.repository.watchlist.ProviderWatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProviderWatchlistScheduler {
    private static final String SUBJECT = "Tasks: Once a day email";
//    private final SimpleEmailService simpleEmailService;
    private final ProviderWatchlistRepository providerWatchlistRepository;

    @Scheduled(cron = "0 */12 * * *")
    public void notifyAboutProviderAvailabilityChange() {

    }
}
