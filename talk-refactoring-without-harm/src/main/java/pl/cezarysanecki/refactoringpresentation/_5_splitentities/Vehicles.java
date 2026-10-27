package pl.cezarysanecki.refactoringpresentation._5_splitentities;

import lombok.RequiredArgsConstructor;

import java.time.Year;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class Vehicles {

    private final Set<Vehicle> vehicles;

    Set<Year> getUniqueProductionYears() {
        return vehicles.stream()
                .map(Vehicle::getProductionYear)
                .collect(Collectors.toUnmodifiableSet());
    }

}
