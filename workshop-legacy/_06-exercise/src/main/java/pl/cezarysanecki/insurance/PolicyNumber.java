package pl.cezarysanecki.insurance;

import java.time.LocalDate;
import java.util.Objects;

public final class PolicyNumber {

    private static final String PATTERN = "POL-\\d{4}-\\d{6}";
    private final String value;

    public PolicyNumber(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Policy number cannot be empty");
        }
        if (!value.matches(PATTERN)) {
            throw new IllegalArgumentException(
                    "Invalid policy number format. Expected format: POL-YYYY-NNNNNN");
        }
        int year = extractYear(value);
        int currentYear = LocalDate.now().getYear();
        if (year < 2000 || year > currentYear) {
            throw new IllegalArgumentException(
                    "Year in policy number must be between 2000 and current year");
        }
    }

    private int extractYear(String policyNumber) {
        String yearPart = policyNumber.substring(4, 8);
        return Integer.parseInt(yearPart);
    }

    public int getYear() {
        return extractYear(value);
    }

    public String getSequenceNumber() {
        return value.substring(9);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolicyNumber that = (PolicyNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
