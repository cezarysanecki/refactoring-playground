package pl.cezarysanecki.legacyworkhop;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StatsFacadeTest {

    private static final Account AN_ACCOUNT = new Account(UUID.randomUUID(), true);

    ExternalStatsSystem externalStatsSystem = mock(ExternalStatsSystem.class);
    EventPublisher<PremiumAccountGranted> eventPublisher = mock(EventPublisher.class);
    StatsRepository statsRepository = mock(StatsRepository.class);

    StatsDownloader statsDownloader = new StatsDownloader(externalStatsSystem, eventPublisher);
    StatsFacade sut = new StatsFacade(statsDownloader, statsRepository);

    @Test
    void updateStatsForExistingAccountUsingDownloadedStats() {
        // given
        when(externalStatsSystem.downloadStatsFor(AN_ACCOUNT.id()))
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
    void createAccountWithDownloadedStatsWhenItDoesNotExist() {
        // given
        when(externalStatsSystem.downloadStatsFor(AN_ACCOUNT.id()))
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

    @Test
    void publishesPremiumAccountGrantedWhenViewsAndLikesMeetThresholds() {
        // given
        when(externalStatsSystem.downloadStatsFor(AN_ACCOUNT.id()))
                .thenReturn(new ExternalStats(AN_ACCOUNT.id(), 1_000_000L, 100_000L));
        when(statsRepository.findByAccountId(AN_ACCOUNT.id()))
                .thenReturn(Optional.of(new Stats(AN_ACCOUNT.id(), 10L, 2L)));
        when(statsRepository.save(any(Stats.class)))
                .thenAnswer(inv -> inv.getArgument(0, Stats.class));

        // when
        sut.getStatsFor(AN_ACCOUNT);

        // then
        verify(eventPublisher).publish(eq(new PremiumAccountGranted(AN_ACCOUNT.id())));
    }

    @Test
    void doesNotPublishEventWhenViewsBelowThreshold() {
        // given
        when(externalStatsSystem.downloadStatsFor(AN_ACCOUNT.id()))
                .thenReturn(new ExternalStats(AN_ACCOUNT.id(), 999_999L, 100_000L));
        when(statsRepository.findByAccountId(AN_ACCOUNT.id()))
                .thenReturn(Optional.of(new Stats(AN_ACCOUNT.id(), 10L, 2L)));
        when(statsRepository.save(any(Stats.class)))
                .thenAnswer(inv -> inv.getArgument(0, Stats.class));

        // when
        sut.getStatsFor(AN_ACCOUNT);

        // then
        verify(eventPublisher, never()).publish(any());
    }

    @Test
    void doesNotPublishEventWhenLikesBelowThreshold() {
        // given
        when(externalStatsSystem.downloadStatsFor(AN_ACCOUNT.id()))
                .thenReturn(new ExternalStats(AN_ACCOUNT.id(), 1_000_000L, 99_999L));
        when(statsRepository.findByAccountId(AN_ACCOUNT.id()))
                .thenReturn(Optional.of(new Stats(AN_ACCOUNT.id(), 10L, 2L)));
        when(statsRepository.save(any(Stats.class)))
                .thenAnswer(inv -> inv.getArgument(0, Stats.class));

        // when
        sut.getStatsFor(AN_ACCOUNT);

        // then
        verify(eventPublisher, never()).publish(any());
    }
}
