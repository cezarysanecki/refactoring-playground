package pl.cezarysanecki.policy;

import java.time.Year;
import java.util.Set;
import java.util.stream.Collectors;

public class InsurancePremiumService {

    private static final double BASE_PREMIUM = 1000.0;

    private final PolicyRepository policyRepository;

    public InsurancePremiumService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public double calculateAnnualPremium(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find policy by id: " + policyId));
        Set<Integer> productionYears = policy.getVehicles()
                .stream()
                .map(Vehicle::getProductionYear)
                .collect(Collectors.toUnmodifiableSet());

        int currentYear = Year.now().getValue();
        double totalAgeMultiplier = productionYears.stream()
                .mapToDouble(productionYear -> currentYear - productionYear)
                .map(age -> 1 + (age * 0.02))
                .sum();

        return BASE_PREMIUM * totalAgeMultiplier;
    }

    public boolean requiresInspection(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find policy by id: " + policyId));
        Set<Integer> productionYears = policy.getVehicles()
                .stream()
                .map(Vehicle::getProductionYear)
                .collect(Collectors.toUnmodifiableSet());

        int tenYearsAgo = Year.now().getValue() - 10;
        return productionYears.stream().anyMatch(productionYear -> productionYear < tenYearsAgo);
    }

}
