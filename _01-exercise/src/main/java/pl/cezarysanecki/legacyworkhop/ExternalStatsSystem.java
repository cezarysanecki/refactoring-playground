package pl.cezarysanecki.legacyworkhop;

import java.util.UUID;

public interface ExternalStatsSystem {
    ExternalStats downloadStatsFor(UUID accountId);
}
