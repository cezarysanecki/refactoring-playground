package pl.cezarysanecki.refactoringpresentation._5_splitentities;

import lombok.RequiredArgsConstructor;

import java.time.Year;
import java.util.Set;

/**
 * Problems:
 *  - very hard to test
 *  - wrong model
 * <p>
 * Solution - split entities
 */
@RequiredArgsConstructor
class VehicleService {

    private final VehicleRepository vehicleRepository;

    void sendAllVehicles(Long policyId) {
        Vehicles vehicles = vehicleRepository.findVehiclesBy(policyId);

        Set<Year> vehiclesProductionYears = vehicles.getUniqueProductionYears();

        if (vehiclesProductionYears.stream().anyMatch(productionYear -> productionYear.isBefore(Year.of(2000)))) {
            throw new IllegalStateException("we do not handle vehicles older than made in 2000");
        }
        // other validations

    }

}
