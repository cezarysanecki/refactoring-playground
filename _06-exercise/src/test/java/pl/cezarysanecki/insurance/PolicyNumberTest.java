package pl.cezarysanecki.insurance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PolicyNumberTest {

    @Test
    void shouldCreateValidPolicyNumber() {
        // given
        String validNumber = "POL-2024-000001";

        // when
        PolicyNumber policyNumber = new PolicyNumber(validNumber);

        // then
        assertEquals(validNumber, policyNumber.getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    void shouldFailForNullOrBlankValue(String value) {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PolicyNumber(value)
        );
        assertEquals("Policy number cannot be empty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "POL2024000001",
            "POL-2024000001",
            "POL-2024-00001",
            "POL-2024-0000001",
            "ABC-2024-000001",
            "pol-2024-000001",
            "POL-XXXX-000001",
            "POL-2024-XXXXXX"
    })
    void shouldFailForInvalidFormat(String invalidNumber) {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PolicyNumber(invalidNumber)
        );
        assertEquals("Invalid policy number format. Expected format: POL-YYYY-NNNNNN", exception.getMessage());
    }

    @Test
    void shouldFailForYearBefore2000() {
        // given
        String numberWithOldYear = "POL-1999-000001";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PolicyNumber(numberWithOldYear)
        );
        assertEquals("Year in policy number must be between 2000 and current year", exception.getMessage());
    }

    @Test
    void shouldFailForFutureYear() {
        // given
        int futureYear = LocalDate.now().getYear() + 1;
        String numberWithFutureYear = "POL-" + futureYear + "-000001";

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new PolicyNumber(numberWithFutureYear)
        );
        assertEquals("Year in policy number must be between 2000 and current year", exception.getMessage());
    }

    @Test
    void shouldAcceptYear2000() {
        // given
        String number = "POL-2000-000001";

        // when
        PolicyNumber policyNumber = new PolicyNumber(number);

        // then
        assertEquals(2000, policyNumber.getYear());
    }

    @Test
    void shouldAcceptCurrentYear() {
        // given
        int currentYear = LocalDate.now().getYear();
        String number = "POL-" + currentYear + "-000001";

        // when
        PolicyNumber policyNumber = new PolicyNumber(number);

        // then
        assertEquals(currentYear, policyNumber.getYear());
    }

    @Test
    void shouldExtractYear() {
        // given
        PolicyNumber policyNumber = new PolicyNumber("POL-2023-000001");

        // when & then
        assertEquals(2023, policyNumber.getYear());
    }

    @Test
    void shouldExtractSequenceNumber() {
        // given
        PolicyNumber policyNumber = new PolicyNumber("POL-2024-123456");

        // when & then
        assertEquals("123456", policyNumber.getSequenceNumber());
    }

    @Test
    void shouldBeEqualForSameValue() {
        // given
        PolicyNumber policyNumber1 = new PolicyNumber("POL-2024-000001");
        PolicyNumber policyNumber2 = new PolicyNumber("POL-2024-000001");

        // when & then
        assertEquals(policyNumber1, policyNumber2);
        assertEquals(policyNumber1.hashCode(), policyNumber2.hashCode());
    }

    @Test
    void shouldNotBeEqualForDifferentValues() {
        // given
        PolicyNumber policyNumber1 = new PolicyNumber("POL-2024-000001");
        PolicyNumber policyNumber2 = new PolicyNumber("POL-2024-000002");

        // when & then
        assertNotEquals(policyNumber1, policyNumber2);
    }

    @Test
    void shouldReturnValueAsToString() {
        // given
        String value = "POL-2024-000001";
        PolicyNumber policyNumber = new PolicyNumber(value);

        // when & then
        assertEquals(value, policyNumber.toString());
    }
}
