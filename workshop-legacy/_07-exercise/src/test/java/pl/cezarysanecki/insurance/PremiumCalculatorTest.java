package pl.cezarysanecki.insurance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PremiumCalculatorTest {

    private PremiumCalculator calculator;

    @BeforeEach
    void setUp() {
        TaxProvider taxProvider = new TaxProvider();
        MarginProvider marginProvider = new MarginProvider();
        calculator = new PremiumCalculator(taxProvider, marginProvider);
    }

    @Test
    void shouldCalculatePremiumForHomePolicy() {
        // given
        Policy policy = new Policy("POL-001", PolicyType.HOME, new BigDecimal("500000"));

        // when
        BigDecimal premium = calculator.calculatePremiumProposal(policy);

        // then
        assertEquals(new BigDecimal("3536.25"), premium);
    }

    @Test
    void shouldCalculateTaxAmountForCarPolicy() {
        // given
        Policy policy = new Policy("POL-002", PolicyType.CAR, new BigDecimal("100000"));

        // when
        BigDecimal tax = calculator.calculateTaxAmount(policy);

        // then
        assertEquals(new BigDecimal("772.80"), tax);
    }

    @Test
    void shouldCalculatePremiumWithNewTax22point5Percent() {
        // given
        Policy policy = new Policy("POL-003", PolicyType.HOME, new BigDecimal("500000"));

        // when
        BigDecimal premium = calculator.calculatePremiumProposal(policy);

        // then
        assertEquals(new BigDecimal("3536.25"), premium);
    }

}
