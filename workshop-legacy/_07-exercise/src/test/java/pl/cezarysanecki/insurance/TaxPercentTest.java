package pl.cezarysanecki.insurance;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TaxPercentTest {

    @Test
    void shouldCreateStandardVatRate() {
        TaxPercent vat = TaxPercent.standard();

        assertEquals("23%", vat.toString());
    }

    @Test
    void shouldCreateReducedVatRate() {
        TaxPercent vat = TaxPercent.reduced();

        assertEquals("8%", vat.toString());
    }

    @Test
    void shouldCreateCustomVatRate() {
        TaxPercent vat = TaxPercent.of(5);

        assertEquals("5%", vat.toString());
    }

    @Test
    void shouldRejectNegativeTaxRate() {
        assertThrows(IllegalArgumentException.class, () -> TaxPercent.of(-1));
    }

    @Test
    void shouldRejectTaxRateOver100() {
        assertThrows(IllegalArgumentException.class, () -> TaxPercent.of(101));
    }

    @Test
    void shouldApplyTaxToNetAmount() {
        // given
        TaxPercent vat = TaxPercent.standard();
        BigDecimal netPremium = new BigDecimal("1000.00");

        // when
        BigDecimal grossPremium = vat.applyTo(netPremium);

        // then
        assertEquals(new BigDecimal("1230.00"), grossPremium);
    }

    @Test
    void shouldCalculateTaxAmount() {
        // given
        TaxPercent vat = TaxPercent.standard();
        BigDecimal netPremium = new BigDecimal("1000.00");

        // when
        BigDecimal taxAmount = vat.calculateTaxOn(netPremium);

        // then
        assertEquals(new BigDecimal("230.00"), taxAmount);
    }

    @Test
    void shouldExtractNetFromGross() {
        // given
        TaxPercent vat = TaxPercent.standard();
        BigDecimal grossPremium = new BigDecimal("1230.00");

        // when
        BigDecimal netPremium = vat.extractNetFrom(grossPremium);

        // then
        assertEquals(new BigDecimal("1000.00"), netPremium);
    }

    @Test
    void shouldExtractTaxFromGross() {
        // given
        TaxPercent vat = TaxPercent.standard();
        BigDecimal grossPremium = new BigDecimal("1230.00");

        // when
        BigDecimal taxAmount = vat.extractTaxFrom(grossPremium);

        // then
        assertEquals(new BigDecimal("230.00"), taxAmount);
    }

    @Test
    void shouldBeEqualWhenSameValue() {
        TaxPercent vat1 = TaxPercent.of(23);
        TaxPercent vat2 = TaxPercent.of(23);

        assertEquals(vat1, vat2);
        assertEquals(vat1.hashCode(), vat2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenDifferentValue() {
        TaxPercent vat23 = TaxPercent.of(23);
        TaxPercent vat8 = TaxPercent.of(8);

        assertNotEquals(vat23, vat8);
    }

    @Test
    void shouldExposeIntValueForBackwardsCompatibility() {
        TaxPercent vat = TaxPercent.standard();

        assertEquals(23, vat.toInt());
    }

}
