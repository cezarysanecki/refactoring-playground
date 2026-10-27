package pl.cezarysanecki.insurance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PolicyServiceIntegrationTest {

    @SpringBootApplication
    static class TestConfig {
    }

    @Autowired
    private PolicyService policyService;

    private String validPolicyNumber;
    private String validPesel;
    private BigDecimal validSumInsured;
    private BigDecimal validPremium;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void setUp() {
        validPolicyNumber = "POL-2024-000001";
        validPesel = "44051401359";
        validSumInsured = new BigDecimal("100000.00");
        validPremium = new BigDecimal("1200.00");
        startDate = LocalDate.of(2024, 1, 1);
        endDate = LocalDate.of(2024, 12, 31);
    }

    @Test
    void shouldCreatePolicyAndPersistToDatabase() {
        // when
        Policy createdPolicy = policyService.createPolicy(
                validPolicyNumber, validPesel, validSumInsured, validPremium, startDate, endDate
        );

        // then
        assertNotNull(createdPolicy);
        assertNotNull(createdPolicy.getId());
        assertEquals(validPolicyNumber, createdPolicy.getPolicyNumber());
        assertEquals(validPesel, createdPolicy.getClientPesel());
        assertEquals(0, validSumInsured.compareTo(createdPolicy.getSumInsured()));
        assertEquals(0, validPremium.compareTo(createdPolicy.getPremium()));
        assertEquals(startDate, createdPolicy.getStartDate());
        assertEquals(endDate, createdPolicy.getEndDate());
        assertEquals("ACTIVE", createdPolicy.getStatus());
    }

    @Test
    void shouldFindPolicyByPolicyNumber() {
        // given
        policyService.createPolicy(
                validPolicyNumber, validPesel, validSumInsured, validPremium, startDate, endDate
        );

        // when
        Policy foundPolicy = policyService.findByPolicyNumber(validPolicyNumber);

        // then
        assertNotNull(foundPolicy);
        assertEquals(validPolicyNumber, foundPolicy.getPolicyNumber());
        assertEquals(validPesel, foundPolicy.getClientPesel());
    }

    @Test
    void shouldReturnNullWhenPolicyNotFound() {
        // given
        String nonExistentNumber = "POL-2024-999999";

        // when
        Policy foundPolicy = policyService.findByPolicyNumber(nonExistentNumber);

        // then
        assertNull(foundPolicy);
    }

    @Test
    void shouldCreateMultiplePoliciesWithDifferentNumbers() {
        // given
        String policyNumber1 = "POL-2024-000001";
        String policyNumber2 = "POL-2024-000002";

        // when
        Policy policy1 = policyService.createPolicy(
                policyNumber1, validPesel, validSumInsured, validPremium, startDate, endDate
        );
        Policy policy2 = policyService.createPolicy(
                policyNumber2, validPesel, validSumInsured, validPremium, startDate, endDate
        );

        // then
        assertNotEquals(policy1.getId(), policy2.getId());
        assertEquals(policyNumber1, policyService.findByPolicyNumber(policyNumber1).getPolicyNumber());
        assertEquals(policyNumber2, policyService.findByPolicyNumber(policyNumber2).getPolicyNumber());
    }

    @Test
    void shouldThrowExceptionForInvalidPesel() {
        // given
        String invalidPesel = "12345678901"; // invalid checksum

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        validPolicyNumber, invalidPesel, validSumInsured, validPremium, startDate, endDate
                )
        );
        assertEquals("Invalid PESEL checksum", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullPesel() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        validPolicyNumber, null, validSumInsured, validPremium, startDate, endDate
                )
        );
        assertEquals("PESEL must have exactly 11 digits", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForPeselWithInvalidLength() {
        // given
        String shortPesel = "1234567890";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        validPolicyNumber, shortPesel, validSumInsured, validPremium, startDate, endDate
                )
        );
        assertEquals("PESEL must have exactly 11 digits", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForPeselWithNonDigits() {
        // given
        String peselWithLetters = "1234567890A";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        validPolicyNumber, peselWithLetters, validSumInsured, validPremium, startDate, endDate
                )
        );
        assertEquals("PESEL can only contain digits", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullSumInsured() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        validPolicyNumber, validPesel, null, validPremium, startDate, endDate
                )
        );
        assertEquals("Sum insured must be greater than zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForZeroSumInsured() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        validPolicyNumber, validPesel, BigDecimal.ZERO, validPremium, startDate, endDate
                )
        );
        assertEquals("Sum insured must be greater than zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNegativeSumInsured() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        validPolicyNumber, validPesel, new BigDecimal("-100"), validPremium, startDate, endDate
                )
        );
        assertEquals("Sum insured must be greater than zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullPremium() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        validPolicyNumber, validPesel, validSumInsured, null, startDate, endDate
                )
        );
        assertEquals("Premium must be greater than zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForZeroPremium() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        validPolicyNumber, validPesel, validSumInsured, BigDecimal.ZERO, startDate, endDate
                )
        );
        assertEquals("Premium must be greater than zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNegativePremium() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        validPolicyNumber, validPesel, validSumInsured, new BigDecimal("-50"), startDate, endDate
                )
        );
        assertEquals("Premium must be greater than zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullPolicyNumber() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        null, validPesel, validSumInsured, validPremium, startDate, endDate
                )
        );
        assertEquals("Policy number cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForEmptyPolicyNumber() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        "", validPesel, validSumInsured, validPremium, startDate, endDate
                )
        );
        assertEquals("Policy number cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForBlankPolicyNumber() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        "   ", validPesel, validSumInsured, validPremium, startDate, endDate
                )
        );
        assertEquals("Policy number cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidPolicyNumberFormat() {
        // given
        String invalidFormat = "POLICY-2024-000001";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        invalidFormat, validPesel, validSumInsured, validPremium, startDate, endDate
                )
        );
        assertEquals("Invalid policy number format. Expected format: POL-YYYY-NNNNNN", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForPolicyNumberWithYearBefore2000() {
        // given
        String oldYearPolicy = "POL-1999-000001";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        oldYearPolicy, validPesel, validSumInsured, validPremium, startDate, endDate
                )
        );
        assertEquals("Year in policy number must be between 2000 and current year", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForPolicyNumberWithFutureYear() {
        // given
        int futureYear = LocalDate.now().getYear() + 1;
        String futureYearPolicy = "POL-" + futureYear + "-000001";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.createPolicy(
                        futureYearPolicy, validPesel, validSumInsured, validPremium, startDate, endDate
                )
        );
        assertEquals("Year in policy number must be between 2000 and current year", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullPolicyNumberInFind() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.findByPolicyNumber(null)
        );
        assertEquals("Policy number cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForEmptyPolicyNumberInFind() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.findByPolicyNumber("")
        );
        assertEquals("Policy number cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForBlankPolicyNumberInFind() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.findByPolicyNumber("   ")
        );
        assertEquals("Policy number cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidPolicyNumberFormatInFind() {
        // given
        String invalidFormat = "INVALID-FORMAT";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.findByPolicyNumber(invalidFormat)
        );
        assertEquals("Invalid policy number format. Expected format: POL-YYYY-NNNNNN", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForPolicyNumberWithYearBefore2000InFind() {
        // given
        String oldYearPolicy = "POL-1999-000001";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.findByPolicyNumber(oldYearPolicy)
        );
        assertEquals("Year in policy number must be between 2000 and current year", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForPolicyNumberWithFutureYearInFind() {
        // given
        int futureYear = LocalDate.now().getYear() + 1;
        String futureYearPolicy = "POL-" + futureYear + "-000001";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.findByPolicyNumber(futureYearPolicy)
        );
        assertEquals("Year in policy number must be between 2000 and current year", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullPolicyNumberInUpdate() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.updateSumInsured(null, validSumInsured)
        );
        assertEquals("Policy number cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForEmptyPolicyNumberInUpdate() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.updateSumInsured("", validSumInsured)
        );
        assertEquals("Policy number cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForBlankPolicyNumberInUpdate() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.updateSumInsured("   ", validSumInsured)
        );
        assertEquals("Policy number cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidPolicyNumberFormatInUpdate() {
        // given
        String invalidFormat = "ABC-123";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.updateSumInsured(invalidFormat, validSumInsured)
        );
        assertEquals("Invalid policy number format. Expected format: POL-YYYY-NNNNNN", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForPolicyNumberWithYearBefore2000InUpdate() {
        // given
        String oldYearPolicy = "POL-1999-000001";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.updateSumInsured(oldYearPolicy, validSumInsured)
        );
        assertEquals("Year in policy number must be between 2000 and current year", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForPolicyNumberWithFutureYearInUpdate() {
        // given
        int futureYear = LocalDate.now().getYear() + 1;
        String futureYearPolicy = "POL-" + futureYear + "-000001";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyService.updateSumInsured(futureYearPolicy, validSumInsured)
        );
        assertEquals("Year in policy number must be between 2000 and current year", exception.getMessage());
    }
}
