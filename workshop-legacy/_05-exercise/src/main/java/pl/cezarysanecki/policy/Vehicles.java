package pl.cezarysanecki.policy;

import java.time.Year;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class Vehicles {

    private final Collection<Vehicle> vehicles;

    public Vehicles(Collection<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public long countProducedAfter(Year year) {
        return productionYears().stream()
                .filter(productionYear -> productionYear.isAfter(year))
                .count();
    }

    public boolean allProducedBefore(Year year) {
        return productionYears().stream()
                .allMatch(productionYear -> productionYear.isBefore(year));
    }

    public Year findOldestProductionYear() {
        return productionYears().stream()
                .min(Year::compareTo)
                .orElseThrow(() -> new IllegalStateException("policy has no vehicles"));
    }

    public boolean hasAnyOlderThan(Year threshold) {
        return productionYears().stream()
                .anyMatch(productionYear -> productionYear.isBefore(threshold));
    }

    public Set<Year> productionYears() {
        return vehicles.stream()
                .map(Vehicle::getProductionYear)
                .collect(Collectors.toUnmodifiableSet());
    }
}
