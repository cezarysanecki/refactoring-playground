package pl.cezarysanecki.refactoringpresentation._3_anemicmodel;

import lombok.Value;

@Value
class Battery {

    int powerLevel;

    boolean isLowPower() {
        return this.powerLevel < 0.06;
    }

    boolean isChargedEnough() {
        return !isLowPower();
    }
}
