package pl.cezarysanecki.legacyworkhop;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class StatsFacade {

    private final StatsDownloader statsDownloader;
    private final StatsRepository statsRepository;

    public StatsFacade(StatsDownloader statsDownloader, StatsRepository statsRepository) {
        this.statsDownloader = statsDownloader;
        this.statsRepository = statsRepository;
    }

    @Transactional
    public Stats getStatsFor(Account account) {
        ExternalStats externalStats = statsDownloader.downloadStatsFor(account.id());

        Stats stats = statsRepository.findByAccountId(account.id()).orElseGet(() -> {
            Stats newStats = new Stats();
            newStats.setAccountId(account.id());
            return newStats;
        });

        stats.setViews(externalStats.views());
        stats.setLikes(externalStats.likes());

        stats = statsRepository.save(stats);

        return stats;
    }

}
