package pl.cezarysanecki.legacyworkhop;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PolicyClaimService {

    private final PolicyRepository policyRepository;

    private final ClaimRepository claimRepository;

    private final Clock clock;

    public PolicyClaimService(PolicyRepository policyRepository, ClaimRepository claimRepository, Clock clock) {
        this.policyRepository = policyRepository;
        this.claimRepository = claimRepository;
        this.clock = clock;
    }

    public Claim registerClaim(Long policyId, String description, double amount) {
        Policy policy = policyRepository.getOne(policyId);
        if (policy == null) {
            throw new IllegalArgumentException("Policy does not exist, id = " + policyId);
        }
        Instant now = clock.instant();
        if (now.isAfter(policy.getExpirationDate())) {
            throw new IllegalStateException("Policy has expired, cannot register claim, id = " + policyId);
        }
        if (now.isBefore(policy.getStartDate())) {
            throw new IllegalStateException("Policy is not yet active, cannot register claim, id = " + policyId);
        }
        Claim claim = new Claim();
        claim.setPolicy(policy);
        claim.setRegisteredAt(now);
        claim.setDescription(description);
        claim.setAmount(amount);
        claim.setDeadline(now.plus(30, ChronoUnit.DAYS));
        return claimRepository.save(claim);
    }
}
