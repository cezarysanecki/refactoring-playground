package pl.cezarysanecki.insurance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AgentCommissionCalculatorTest {

    private AgentCommissionCalculator calculator;

    @BeforeEach
    void setUp() {
        TaxProvider taxProvider = new TaxProvider();
        calculator = new AgentCommissionCalculator(taxProvider);
    }

    @Test
    void shouldCalculateGrossCommissionWithCurrentTax() {
        // given
        BigDecimal netPremium = new BigDecimal("1000");

        // when
        BigDecimal grossCommission = calculator.calculateGrossCommission(netPremium, PolicyType.HOME);

        // then
        assertEquals(new BigDecimal("98.40"), grossCommission);
    }

    @Test
    void shouldCalculateTaxOnCommission() {
        // given
        BigDecimal netPremium = new BigDecimal("1000");

        // when
        BigDecimal taxOnCommission = calculator.calculateTaxOnCommission(netPremium, PolicyType.CAR);

        // then
        assertEquals(new BigDecimal("18.40"), taxOnCommission);
    }

    @Test
    void shouldCalculateGrossCommissionWithNewTax22point5Percent() {
        // given
        BigDecimal netPremium = new BigDecimal("1000");

        // when
        BigDecimal grossCommission = calculator.calculateGrossCommission(netPremium, PolicyType.HOME);

        // then
        assertEquals(new BigDecimal("98.40"), grossCommission);
    }

    @Test
    void shouldCalculateTaxOnCommissionWithNewTax22point5Percent() {
        // given
        BigDecimal netPremium = new BigDecimal("1000");

        // when
        BigDecimal taxOnCommission = calculator.calculateTaxOnCommission(netPremium, PolicyType.HOME);

        // then
        assertEquals(new BigDecimal("18.40"), taxOnCommission);
    }

}
