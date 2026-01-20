package pl.cezarysanecki.legacyworkhop;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StatsDownloaderTest {

    private static final UUID AN_ACCOUNT_ID = UUID.randomUUID();

    ExternalStatsSystem externalStatsSystem = mock(ExternalStatsSystem.class);
    EventPublisher<PremiumAccountGranted> eventPublisher = mock(EventPublisher.class);

    StatsDownloader sut = new StatsDownloader(externalStatsSystem, eventPublisher);

    @Test
    void returnsDownloadedStatsMappedToAccountId() {
        // given
        when(externalStatsSystem.downloadStatsFor(AN_ACCOUNT_ID))
                .thenReturn(new ExternalStats(AN_ACCOUNT_ID, 123L, 45L));

        // when
        ExternalStats result = sut.downloadStatsFor(AN_ACCOUNT_ID);

        // then
        assertEquals(AN_ACCOUNT_ID, result.accountId());
        assertEquals(123L, result.views());
        assertEquals(45L, result.likes());

        verify(externalStatsSystem).downloadStatsFor(eq(AN_ACCOUNT_ID));
    }

    @Test
    void publishesPremiumAccountGrantedWhenViewsAndLikesMeetThresholds() {
        // given
        when(externalStatsSystem.downloadStatsFor(AN_ACCOUNT_ID))
                .thenReturn(new ExternalStats(AN_ACCOUNT_ID, 1_000_000L, 100_000L));

        // when
        sut.downloadStatsFor(AN_ACCOUNT_ID);

        // then
        verify(eventPublisher).publish(eq(new PremiumAccountGranted(AN_ACCOUNT_ID)));
    }

    @Test
    void doesNotPublishEventWhenViewsBelowThreshold() {
        // given
        when(externalStatsSystem.downloadStatsFor(AN_ACCOUNT_ID))
                .thenReturn(new ExternalStats(AN_ACCOUNT_ID, 999_999L, 100_000L));

        // when
        sut.downloadStatsFor(AN_ACCOUNT_ID);

        // then
        verify(eventPublisher, never()).publish(any());
    }

    @Test
    void doesNotPublishEventWhenLikesBelowThreshold() {
        // given
        when(externalStatsSystem.downloadStatsFor(AN_ACCOUNT_ID))
                .thenReturn(new ExternalStats(AN_ACCOUNT_ID, 1_000_000L, 99_999L));

        // when
        sut.downloadStatsFor(AN_ACCOUNT_ID);

        // then
        verify(eventPublisher, never()).publish(any());
    }
}

