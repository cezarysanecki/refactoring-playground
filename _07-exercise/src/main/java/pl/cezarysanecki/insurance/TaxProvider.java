package pl.cezarysanecki.insurance;

import org.springframework.stereotype.Component;

@Component
public class TaxProvider {

    public int getTaxPercentForPolicyType(PolicyType type) {
        return switch (type) {
            case HOME, CAR, TRAVEL -> 23;
            case LIFE -> 8;
        };
    }
}
