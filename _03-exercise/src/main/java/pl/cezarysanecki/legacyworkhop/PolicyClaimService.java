package pl.cezarysanecki.legacyworkhop;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PolicyClaimService {

    private PolicyRepository policyRepository;

    private ClaimRepository claimRepository;

    //..

    public Claim registerClaim(Long policyId, String description, double amount) {
        Policy policy = policyRepository.getOne(policyId);
        if (policy == null) {
            throw new IllegalArgumentException("Policy does not exist, id = " + policyId);
        }
        if (Instant.now().isAfter(policy.getExpirationDate())) {
            throw new IllegalStateException("Policy has expired, cannot register claim, id = " + policyId);
        }
        if (Instant.now().isBefore(policy.getStartDate())) {
            throw new IllegalStateException("Policy is not yet active, cannot register claim, id = " + policyId);
        }
        Claim claim = new Claim();
        claim.setPolicy(policy);
        claim.setRegisteredAt(Instant.now());
        claim.setDescription(description);
        claim.setAmount(amount);
        claim.setDeadline(Instant.now().plus(30, ChronoUnit.DAYS));
        return claimRepository.save(claim);
    }
}
