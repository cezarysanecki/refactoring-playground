package pl.cezarysanecki.refactoringpresentation._2_bigobject;

import java.time.Year;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Problems:
 *  - hard to test
 * <p>
 * Solution - extract method which is needed to test
 */
class VehicleValidator {

    void validate(Policy policy) {
        validate(policy.getVehicles());
    }

    void validate(Set<Vehicle> vehicles) {
        Set<Year> vehiclesProductionYears = vehicles.stream()
                .map(Vehicle::getProductionYear)
                .collect(Collectors.toUnmodifiableSet());
        validateIfVehiclesAreNotTooOld(vehiclesProductionYears);
        // ... other validators
    }

    @OnlyTestUsage
    static void validateIfVehiclesAreNotTooOld(Set<Year> vehiclesProductionYears) {
        boolean isAnyTooOldVehicle = vehiclesProductionYears.stream()
                .anyMatch(productionYear -> productionYear.isBefore(Year.of(2000)));
        if (isAnyTooOldVehicle) {
            throw new IllegalStateException("we do not handle vehicles older than made in 2000");
        }
    }

}
