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
        BigDecimal netCommission = netPremium
                .multiply(COMMISSION_RATE)
                .divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);

        int taxPercent = taxProvider.getTaxPercentForPolicyType(policyType);
        BigDecimal taxMultiplier = new BigDecimal(taxPercent).divide(ONE_HUNDRED, 4, RoundingMode.HALF_UP);

        return netCommission.add(netCommission.multiply(taxMultiplier))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTaxOnCommission(BigDecimal netPremium, PolicyType policyType) {
        BigDecimal netCommission = netPremium
                .multiply(COMMISSION_RATE)
                .divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);

        int taxPercent = taxProvider.getTaxPercentForPolicyType(policyType);
        BigDecimal taxMultiplier = new BigDecimal(taxPercent).divide(ONE_HUNDRED, 4, RoundingMode.HALF_UP);

        return netCommission.multiply(taxMultiplier).setScale(2, RoundingMode.HALF_UP);
    }
}
