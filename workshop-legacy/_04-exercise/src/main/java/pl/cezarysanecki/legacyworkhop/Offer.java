package pl.cezarysanecki.legacyworkhop;

import java.time.LocalDate;

public record Offer(String id, String name, LocalDate expirationDate) {
}
