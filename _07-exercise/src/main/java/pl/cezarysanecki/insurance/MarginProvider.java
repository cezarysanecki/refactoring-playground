package pl.cezarysanecki.insurance;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class MarginProvider {

    public BigDecimal getMarginPercent(PolicyType type) {
        return switch (type) {
            case HOME -> new BigDecimal("15");
            case CAR -> new BigDecimal("12");
            case LIFE -> new BigDecimal("20");
            case TRAVEL -> new BigDecimal("25");
        };
    }
}
