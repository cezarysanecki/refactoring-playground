package pl.cezarysanecki.legacyworkhop;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PolicyClaimServiceTest {

    private static final Instant NOW = Instant.parse("2024-06-15T12:00:00Z");

    private final PolicyRepository policyRepository = mock(PolicyRepository.class);
    private final ClaimRepository claimRepository = mock(ClaimRepository.class);
    private final Clock clock = Clock.fixed(NOW, ZoneId.of("UTC"));

    private final PolicyClaimService service = new PolicyClaimService(policyRepository, claimRepository, clock);

    @Test
    void shouldRejectClaimWhenPolicyHasExpired() {
        // given
        Policy expiredPolicy = expiredPolicy(NOW);
        when(policyRepository.getOne(1L)).thenReturn(expiredPolicy);

        // when & then
        assertThrows(
                IllegalStateException.class,
                () -> service.registerClaim(1L, "Damage", 1000.0)
        );
    }

    @Test
    void shouldRejectClaimWhenPolicyIsNotYetActive() {
        // given
        Policy futurePolicy = futurePolicy(NOW);
        when(policyRepository.getOne(2L)).thenReturn(futurePolicy);

        // when & then
        assertThrows(
                IllegalStateException.class,
                () -> service.registerClaim(2L, "Damage", 500.0)
        );
    }

    @Test
    void shouldSetDeadlineTo30DaysFromRegistration() {
        // given
        Policy activePolicy = activePolicy(NOW);

        when(policyRepository.getOne(3L)).thenReturn(activePolicy);
        when(claimRepository.save(org.mockito.ArgumentMatchers.any(Claim.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Claim claim = service.registerClaim(3L, "Water damage", 2500.0);

        // then
        assertEquals(NOW, claim.getRegisteredAt());
        assertEquals(NOW.plus(30, ChronoUnit.DAYS), claim.getDeadline());
    }

    private static Policy expiredPolicy(Instant now) {
        Policy expiredPolicy = new Policy();
        expiredPolicy.setId(1L);
        expiredPolicy.setStartDate(now.minus(365, ChronoUnit.DAYS));
        expiredPolicy.setExpirationDate(now.minus(1, ChronoUnit.DAYS));
        return expiredPolicy;
    }

    private static Policy futurePolicy(Instant now) {
        Policy futurePolicy = new Policy();
        futurePolicy.setId(2L);
        futurePolicy.setStartDate(now.plus(30, ChronoUnit.DAYS));
        futurePolicy.setExpirationDate(now.plus(395, ChronoUnit.DAYS));
        return futurePolicy;
    }

    private static Policy activePolicy(Instant now) {
        Policy activePolicy = new Policy();
        activePolicy.setId(3L);
        activePolicy.setStartDate(now.minus(30, ChronoUnit.DAYS));
        activePolicy.setExpirationDate(now.plus(335, ChronoUnit.DAYS));
        return activePolicy;
    }

}
