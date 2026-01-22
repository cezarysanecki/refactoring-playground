package pl.cezarysanecki.insurance;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class AgentCommissionCalculator {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    private static final BigDecimal COMMISSION_RATE = new BigDecimal("8");

    private final TaxProvider taxProvider;

    public AgentCommissionCalculator(TaxProvider taxProvider) {
        this.taxProvider = taxProvider;
    }

    public BigDecimal calculateGrossCommission(BigDecimal netPremium, PolicyType policyType) {
        BigDecimal netCommission = calculateNetCommission(netPremium);
        TaxPercent tax = taxProvider.getTaxFor(policyType);

        return tax.applyTo(netCommission);
    }

    public BigDecimal calculateTaxOnCommission(BigDecimal netPremium, PolicyType policyType) {
        BigDecimal netCommission = calculateNetCommission(netPremium);
        TaxPercent tax = taxProvider.getTaxFor(policyType);

        return tax.calculateTaxOn(netCommission);
    }

    private BigDecimal calculateNetCommission(BigDecimal netPremium) {
        return netPremium
                .multiply(COMMISSION_RATE)
                .divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);
    }
}
