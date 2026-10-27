package pl.cezarysanecki.insurance;

import org.springframework.stereotype.Component;

@Component
public class TaxProvider {

    public TaxPercent getTaxFor(PolicyType type) {
        return switch (type) {
            case HOME, CAR, TRAVEL -> TaxPercent.standard();
            case LIFE -> TaxPercent.reduced();
        };
    }
}
