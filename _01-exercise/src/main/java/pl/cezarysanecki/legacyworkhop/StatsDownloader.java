package pl.cezarysanecki.legacyworkhop;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class StatsDownloader {

    private final ExternalStatsSystem externalStatsSystem;
    private final EventPublisher<PremiumAccountGranted> eventPublisher;

    public StatsDownloader(
            ExternalStatsSystem externalStatsSystem,
            EventPublisher<PremiumAccountGranted> eventPublisher
    ) {
        this.externalStatsSystem = externalStatsSystem;
        this.eventPublisher = eventPublisher;
    }

    public ExternalStats downloadStatsFor(UUID accountId) {
        var externalStats = externalStatsSystem.downloadStatsFor(accountId);

        if (externalStats.views() >= 1_000_000 && externalStats.likes() >= 100_000) {
            eventPublisher.publish(new PremiumAccountGranted(accountId));
        }

        return new ExternalStats(
                accountId,
                externalStats.views(),
                externalStats.likes()
        );
    }

}
