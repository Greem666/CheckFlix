package com.maciej.checkflix.watchlist.scheduler;

import com.maciej.checkflix.watchlist.config.AutomatedMailsConfig;
import com.maciej.checkflix.watchlist.domain.ProviderDetailsDto;
import com.maciej.checkflix.watchlist.domain.ProviderWatchlist;
import com.maciej.checkflix.watchlist.domain.ProviderWatchlistDto;
import com.maciej.checkflix.watchlist.domain.mail.Mail;
import com.maciej.checkflix.watchlist.mapper.ProviderWatchlistMapper;
import com.maciej.checkflix.watchlist.repository.ProviderWatchlistRepository;
import com.maciej.checkflix.watchlist.service.ProviderWatchlistService;
import com.maciej.checkflix.watchlist.service.WatchlistEmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProviderWatchlistScheduler {
    private static final Logger logger = LoggerFactory.getLogger(ProviderWatchlistScheduler.class);

    private static final String AVAILABLE_PROVIDER = "A provider became available";

    private final WatchlistEmailService watchlistEmailService;
    private final AutomatedMailsConfig automatedMailsConfig;
    private final ProviderWatchlistRepository providerWatchlistRepository;
    private final ProviderWatchlistService providerWatchlistService;
    private final ProviderWatchlistMapper providerWatchlistMapper;

    @Scheduled(cron = "0 */12 * * * ?")
    public void notifyAboutProviderAvailabilityChange() {
        logger.info("Starting a watchlist items sweep...");
        List<ProviderWatchlist> allTickets = providerWatchlistRepository.findAll();

        for (ProviderWatchlist ticket: allTickets) {
            ProviderWatchlistDto ticketDto = providerWatchlistMapper.mapToProviderWatchlistDto(ticket);
            List<ProviderDetailsDto> availableProviders = providerWatchlistService.getProvidersFor(
                    ticketDto.getImdbId(), ticketDto.getCountry(), ticketDto.getProviderType());

            if (availableProviders.size() > 0) {
                logger.info("New providers found for " + ticketDto.toString());
                watchlistEmailService.send(constructMail(availableProviders, ticketDto));

                logger.info("Deleting " + ticketDto.toString() + " from the watchlist");
                // delete item from watchlist
                providerWatchlistRepository.deleteById(ticket.getId());
            }
        }
    }

    private Mail constructMail(List<ProviderDetailsDto> availableProviders, ProviderWatchlistDto ticketDto) {
        StringBuilder message = new StringBuilder(String.format(
                "Hi %s,\n\nThe following providers hosting %s in %s option are now available:\n",
                ticketDto.getUsername(), ticketDto.getMovieName(), ticketDto.getProviderType()));

        for (ProviderDetailsDto provider: availableProviders) {
            message.append(String.format("- %s\n", provider.getProviderName()));
        }

        message.append("\nKind regards,\nCheckflix Team");

        return new Mail(automatedMailsConfig.getAdminEmail(), ticketDto.getEmail(), AVAILABLE_PROVIDER, message.toString());
    }
}
