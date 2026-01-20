package pl.cezarysanecki.legacyworkhop;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatsFacadeTest {

    private static final Account AN_ACCOUNT = new Account(UUID.randomUUID(), true);

    StatsDownloader statsDownloader = mock(StatsDownloader.class);
    StatsRepository statsRepository = mock(StatsRepository.class);

    StatsFacade sut = new StatsFacade(statsDownloader, statsRepository);

    @Test
    void setReturnedValuesFromStatsDownloaderInExistingAccount() {
        // given
        when(statsDownloader.downloadStatsFor(AN_ACCOUNT.id()))
                .thenReturn(new ExternalStats(AN_ACCOUNT.id(), 100L, 20L));
        when(statsRepository.findByAccountId(AN_ACCOUNT.id()))
                .thenReturn(Optional.of(new Stats(AN_ACCOUNT.id(), 10L, 2L)));
        when(statsRepository.save(any(Stats.class)))
                .thenAnswer(inv -> inv.getArgument(0, Stats.class));

        // when
        Stats result = sut.getStatsFor(AN_ACCOUNT);

        // then
        assertEquals(AN_ACCOUNT.id(), result.getAccountId());
        assertEquals(100L, result.getViews());
        assertEquals(20L, result.getLikes());
    }

    @Test
    void setReturnedValuesFromStatsDownloaderInNewlyCreatedAccount() {
        // given
        when(statsDownloader.downloadStatsFor(AN_ACCOUNT.id()))
                .thenReturn(new ExternalStats(AN_ACCOUNT.id(), 100L, 20L));
        when(statsRepository.findByAccountId(AN_ACCOUNT.id()))
                .thenReturn(Optional.empty());
        when(statsRepository.save(any(Stats.class)))
                .thenAnswer(inv -> inv.getArgument(0, Stats.class));

        // when
        Stats result = sut.getStatsFor(AN_ACCOUNT);

        // then
        assertEquals(AN_ACCOUNT.id(), result.getAccountId());
        assertEquals(100L, result.getViews());
        assertEquals(20L, result.getLikes());
    }
}
