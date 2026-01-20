package pl.cezarysanecki.legacyworkhop;

import java.util.UUID;

public record ExternalStats(UUID accountId, Long views, Long likes) {
}
