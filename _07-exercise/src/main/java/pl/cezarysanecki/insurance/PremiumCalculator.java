package pl.cezarysanecki.insurance;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PremiumCalculator {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    private final TaxProvider taxProvider;
    private final MarginProvider marginProvider;

    public PremiumCalculator(TaxProvider taxProvider, MarginProvider marginProvider) {
        this.taxProvider = taxProvider;
        this.marginProvider = marginProvider;
    }

    public BigDecimal calculatePremiumProposal(Policy policy) {
        BigDecimal basePremium = calculateBasePremium(policy);
        BigDecimal marginPercent = marginProvider.getMarginPercent(policy.getType());
        int taxPercent = taxProvider.getTaxPercentForPolicyType(policy.getType());

        BigDecimal premiumWithMargin = basePremium.add(
                basePremium.multiply(marginPercent).divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP)
        );

        BigDecimal taxMultiplier = new BigDecimal(taxPercent).divide(ONE_HUNDRED, 4, RoundingMode.HALF_UP);
        BigDecimal finalPremium = premiumWithMargin.add(
                premiumWithMargin.multiply(taxMultiplier)
        );

        return finalPremium.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTaxAmount(Policy policy) {
        BigDecimal basePremium = calculateBasePremium(policy);
        BigDecimal marginPercent = marginProvider.getMarginPercent(policy.getType());
        int taxPercent = taxProvider.getTaxPercentForPolicyType(policy.getType());

        BigDecimal premiumWithMargin = basePremium.add(
                basePremium.multiply(marginPercent).divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP)
        );

        BigDecimal taxMultiplier = new BigDecimal(taxPercent).divide(ONE_HUNDRED, 4, RoundingMode.HALF_UP);
        return premiumWithMargin.multiply(taxMultiplier).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateBasePremium(Policy policy) {
        BigDecimal riskFactor = switch (policy.getType()) {
            case HOME -> new BigDecimal("0.005");
            case CAR -> new BigDecimal("0.03");
            case LIFE -> new BigDecimal("0.01");
            case TRAVEL -> new BigDecimal("0.02");
        };
        return policy.getInsuredAmount().multiply(riskFactor).setScale(2, RoundingMode.HALF_UP);
    }
}
