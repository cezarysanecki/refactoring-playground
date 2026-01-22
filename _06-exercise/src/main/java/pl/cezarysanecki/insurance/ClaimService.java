package pl.cezarysanecki.insurance;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ClaimService {

    private static final String POLICY_NUMBER_PATTERN = "POL-\\d{4}-\\d{6}";

    private final ClaimRepository claimRepository;
    private final PolicyService policyService;

    public ClaimService(ClaimRepository claimRepository, PolicyService policyService) {
        this.claimRepository = claimRepository;
        this.policyService = policyService;
    }

    public Claim registerClaim(String claimNumber, String policyNumber,
                               BigDecimal claimedAmount, LocalDate incidentDate,
                               String description) {

        if (claimNumber == null || claimNumber.isBlank()) {
            throw new IllegalArgumentException("Claim number cannot be empty");
        }
        if (!claimNumber.matches("CLM-\\d{4}-\\d{6}")) {
            throw new IllegalArgumentException("Invalid claim number format. " +
                    "Expected format: CLM-YYYY-NNNNNN");
        }

        if (policyNumber == null || policyNumber.isBlank()) {
            throw new IllegalArgumentException("Policy number cannot be empty");
        }
        if (!policyNumber.matches(POLICY_NUMBER_PATTERN)) {
            throw new IllegalArgumentException(
                    "Invalid policy number format. Expected format: POL-YYYY-NNNNNN");
        }
        String yearPart = policyNumber.substring(4, 8);
        int year = Integer.parseInt(yearPart);
        int currentYear = LocalDate.now().getYear();
        if (year < 2000 || year > currentYear) {
            throw new IllegalArgumentException(
                    "Year in policy number must be between 2000 and current year");
        }

        Policy policy = policyService.findByPolicyNumber(policyNumber);
        if (policy == null) {
            throw new IllegalArgumentException("Policy with given number does not exist");
        }

        if (claimedAmount == null || claimedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Claim amount must be greater than zero");
        }

        if (claimedAmount.compareTo(policy.getSumInsured()) > 0) {
            throw new IllegalArgumentException("Claim amount cannot exceed sum insured");
        }

        Claim claim = new Claim(claimNumber, policyNumber, claimedAmount, incidentDate, description);
        return claimRepository.save(claim);
    }

    public void approveClaim(String claimNumber, BigDecimal approvedAmount) {
        if (claimNumber == null || claimNumber.isBlank()) {
            throw new IllegalArgumentException("Claim number cannot be empty");
        }
        if (!claimNumber.matches("CLM-\\d{4}-\\d{6}")) {
            throw new IllegalArgumentException("Invalid claim number format");
        }

        if (approvedAmount == null || approvedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Approved amount must be greater than zero");
        }

        Claim claim = findByClaimNumber(claimNumber);
        if (claim == null) {
            throw new IllegalArgumentException("Claim does not exist");
        }

        if (approvedAmount.compareTo(claim.getClaimedAmount()) > 0) {
            throw new IllegalArgumentException("Approved amount cannot be greater than claimed amount");
        }

        claim.setApprovedAmount(approvedAmount);
        claim.setStatus("APPROVED");
    }

    public Claim findByClaimNumber(String claimNumber) {
        if (claimNumber == null || claimNumber.isBlank()) {
            throw new IllegalArgumentException("Claim number cannot be empty");
        }
        if (!claimNumber.matches("CLM-\\d{4}-\\d{6}")) {
            throw new IllegalArgumentException("Invalid claim number format");
        }

        return claimRepository.findByClaimNumber(claimNumber);
    }

    public BigDecimal calculateTotalClaimedForPolicy(String policyNumber) {

        if (policyNumber == null || policyNumber.isBlank()) {
            throw new IllegalArgumentException("Policy number cannot be empty");
        }
        if (!policyNumber.matches(POLICY_NUMBER_PATTERN)) {
            throw new IllegalArgumentException(
                    "Invalid policy number format. Expected format: POL-YYYY-NNNNNN");
        }
        String yearPart = policyNumber.substring(4, 8);
        int year = Integer.parseInt(yearPart);
        int currentYear = LocalDate.now().getYear();
        if (year < 2000 || year > currentYear) {
            throw new IllegalArgumentException(
                    "Year in policy number must be between 2000 and current year");
        }

        return claimRepository.findAllByPolicyNumber(policyNumber)
                .stream()
                .map(Claim::getClaimedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
