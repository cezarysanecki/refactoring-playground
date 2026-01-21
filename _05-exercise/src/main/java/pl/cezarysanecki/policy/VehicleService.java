package pl.cezarysanecki.policy;

import java.util.Set;
import java.util.stream.Collectors;

public class VehicleService {

    private final PolicyRepository policyRepository;

    public VehicleService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public double calculateDiscountFor(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find policy by id: " + policyId));
        Set<Integer> productionYears = policy.getVehicles()
                .stream()
                .map(Vehicle::getProductionYear)
                .collect(Collectors.toUnmodifiableSet());

        if (productionYears.stream().anyMatch(productionYear -> productionYear < 2000)) {
            throw new IllegalStateException("we do not handle vehicles older than made in 2000");
        }

        long newVehiclesCount = productionYears.stream()
                .filter(productionYear -> productionYear > 2020)
                .count();

        if (newVehiclesCount <= 5) {
            return newVehiclesCount * 5.0;
        }
        return 25.0;
    }

    public boolean isEligibleForClassicCarDiscount(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find policy by id: " + policyId));
        Set<Integer> productionYears = policy.getVehicles()
                .stream()
                .map(Vehicle::getProductionYear)
                .collect(Collectors.toUnmodifiableSet());

        return productionYears.stream().allMatch(productionYear -> productionYear < 1990);
    }

    public int findOldestVehicleProductionYear(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find policy by id: " + policyId));
        Set<Integer> productionYears = policy.getVehicles()
                .stream()
                .map(Vehicle::getProductionYear)
                .collect(Collectors.toUnmodifiableSet());

        return productionYears.stream()
                .min(Integer::compareTo)
                .orElseThrow(() -> new IllegalStateException("policy has no vehicles"));
    }

}
