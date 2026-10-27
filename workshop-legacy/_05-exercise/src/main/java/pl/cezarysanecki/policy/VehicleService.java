package pl.cezarysanecki.policy;

import java.time.Year;

public class VehicleService {

    private final PolicyRepository policyRepository;

    public VehicleService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public double calculateDiscountFor(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find policy by id: " + policyId));
        Vehicles vehicles = new Vehicles(policy.getVehicles());

        if (vehicles.hasAnyOlderThan(Year.of(2000))) {
            throw new IllegalStateException("we do not handle vehicles older than made in 2000");
        }

        long newVehiclesCount = vehicles.countProducedAfter(Year.of(2020));

        if (newVehiclesCount <= 5) {
            return newVehiclesCount * 5.0;
        }
        return 25.0;
    }

    public boolean isEligibleForClassicCarDiscount(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find policy by id: " + policyId));
        Vehicles vehicles = new Vehicles(policy.getVehicles());

        return vehicles.allProducedBefore(Year.of(1990));
    }

    public Year findOldestVehicleProductionYear(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find policy by id: " + policyId));
        Vehicles vehicles = new Vehicles(policy.getVehicles());

        return vehicles.findOldestProductionYear();
    }

}
