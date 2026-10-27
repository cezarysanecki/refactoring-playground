package pl.cezarysanecki.policy;

import java.time.Year;

public class InsurancePremiumService {

    private static final double BASE_PREMIUM = 1000.0;

    private final PolicyRepository policyRepository;

    public InsurancePremiumService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public double calculateAnnualPremium(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find policy by id: " + policyId));
        Vehicles vehicles = new Vehicles(policy.getVehicles());

        Year currentYear = Year.now();
        return BASE_PREMIUM * vehicles.productionYears()
                .stream()
                .mapToDouble(productionYear -> currentYear.getValue() - productionYear.getValue())
                .map(age -> 1 + (age * 0.02))
                .sum();
    }

    public boolean requiresInspection(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find policy by id: " + policyId));
        Vehicles vehicles = new Vehicles(policy.getVehicles());

        return vehicles.hasAnyOlderThan(Year.now().minusYears(10));
    }

}
