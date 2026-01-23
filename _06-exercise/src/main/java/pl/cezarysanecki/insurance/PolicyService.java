package pl.cezarysanecki.insurance;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public Policy createPolicy(String policyNumber, String clientPesel,
                               BigDecimal sumInsured, BigDecimal premium,
                               LocalDate startDate, LocalDate endDate) {

        if (clientPesel == null || clientPesel.length() != 11) {
            throw new IllegalArgumentException("PESEL must have exactly 11 digits");
        }
        if (!clientPesel.matches("\\d{11}")) {
            throw new IllegalArgumentException("PESEL can only contain digits");
        }
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(clientPesel.charAt(i)) * weights[i];
        }
        int controlDigit = (10 - (sum % 10)) % 10;
        if (controlDigit != Character.getNumericValue(clientPesel.charAt(10))) {
            throw new IllegalArgumentException("Invalid PESEL checksum");
        }

        if (sumInsured == null || sumInsured.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Sum insured must be greater than zero");
        }
        if (premium == null || premium.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Premium must be greater than zero");
        }

        PolicyNumber policyNumberVO = new PolicyNumber(policyNumber);
        Policy policy = new Policy(policyNumberVO.getValue(), clientPesel, sumInsured, premium, startDate, endDate);
        return policyRepository.save(policy);
    }

    public Policy findByPolicyNumber(String policyNumber) {
        PolicyNumber policyNumberVO = new PolicyNumber(policyNumber);
        return policyRepository.findByPolicyNumber(policyNumberVO.getValue()).orElse(null);
    }

    public void updateSumInsured(String policyNumber, BigDecimal newSumInsured) {
        if (newSumInsured == null || newSumInsured.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Sum insured must be greater than zero");
        }

        PolicyNumber policyNumberVO = new PolicyNumber(policyNumber);
        policyRepository.findByPolicyNumber(policyNumberVO.getValue()).ifPresent(policy -> {
            policy.setSumInsured(newSumInsured);
            policyRepository.save(policy);
        });
    }

    public BigDecimal calculateTotalPremiumForClient(String clientPesel) {
        if (clientPesel == null || clientPesel.length() != 11) {
            throw new IllegalArgumentException("PESEL must have exactly 11 digits");
        }
        if (!clientPesel.matches("\\d{11}")) {
            throw new IllegalArgumentException("PESEL can only contain digits");
        }
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(clientPesel.charAt(i)) * weights[i];
        }
        int controlDigit = (10 - (sum % 10)) % 10;
        if (controlDigit != Character.getNumericValue(clientPesel.charAt(10))) {
            throw new IllegalArgumentException("Invalid PESEL checksum");
        }

        return policyRepository.findByClientPesel(clientPesel).stream()
                .map(Policy::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
