package pl.cezarysanecki.insurance;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ReportService {

    private final PolicyService policyService;
    private final ClaimService claimService;

    public ReportService(PolicyService policyService, ClaimService claimService) {
        this.policyService = policyService;
        this.claimService = claimService;
    }

    public String generateClientReport(String clientPesel) {
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

        BigDecimal totalPremium = policyService.calculateTotalPremiumForClient(clientPesel);

        String formattedPremium = totalPremium.setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace(".", ",") + " PLN";

        return String.format("Client report for %s\nTotal premium: %s",
                maskPesel(clientPesel), formattedPremium);
    }

    public String generatePolicyReport(String policyNumber) {
        PolicyNumber policyNumberVO = new PolicyNumber(policyNumber);

        Policy policy = policyService.findByPolicyNumber(policyNumberVO.getValue());
        if (policy == null) {
            return "Policy not found";
        }

        BigDecimal totalClaimed = claimService.calculateTotalClaimedForPolicy(policyNumberVO.getValue());

        String formattedSumInsured = policy.getSumInsured()
                .setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace(".", ",") + " PLN";

        String formattedClaimed = totalClaimed
                .setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace(".", ",") + " PLN";

        String formattedPremium = policy.getPremium()
                .setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace(".", ",") + " PLN";

        return String.format(
                "Policy: %s\nSum insured: %s\nPremium: %s\nTotal claims: %s",
                policyNumber, formattedSumInsured, formattedPremium, formattedClaimed);
    }

    private String maskPesel(String pesel) {
        return pesel.substring(0, 2) + "*******" + pesel.substring(9);
    }
}
