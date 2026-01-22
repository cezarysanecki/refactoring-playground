package pl.cezarysanecki.insurance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class TaxPercent {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    private final int value;

    private TaxPercent(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Stawka podatku nie może być ujemna: " + value);
        }
        if (value > 100) {
            throw new IllegalArgumentException("Stawka podatku nie może przekraczać 100%: " + value);
        }
        this.value = value;
    }

    public static TaxPercent of(int percent) {
        return new TaxPercent(percent);
    }

    public static TaxPercent standard() {
        return new TaxPercent(23);
    }

    public static TaxPercent reduced() {
        return new TaxPercent(8);
    }

    public static TaxPercent zero() {
        return new TaxPercent(0);
    }

    public BigDecimal applyTo(BigDecimal netAmount) {
        BigDecimal taxAmount = calculateTaxOn(netAmount);
        return netAmount.add(taxAmount).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTaxOn(BigDecimal netAmount) {
        return netAmount
                .multiply(toMultiplier())
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal extractNetFrom(BigDecimal grossAmount) {
        BigDecimal divisor = BigDecimal.ONE.add(toMultiplier());
        return grossAmount.divide(divisor, 2, RoundingMode.HALF_UP);
    }

    public BigDecimal extractTaxFrom(BigDecimal grossAmount) {
        BigDecimal netAmount = extractNetFrom(grossAmount);
        return grossAmount.subtract(netAmount);
    }

    private BigDecimal toMultiplier() {
        return new BigDecimal(value).divide(ONE_HUNDRED, 4, RoundingMode.HALF_UP);
    }

    public int toInt() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxPercent that = (TaxPercent) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value + "%";
    }
}
