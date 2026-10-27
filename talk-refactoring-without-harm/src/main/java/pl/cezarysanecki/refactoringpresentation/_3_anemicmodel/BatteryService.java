package pl.cezarysanecki.refactoringpresentation._3_anemicmodel;

/**
 * Problems:
 *  - duplicated logic (anemic model)
 * <p>
 * Solution - extract method and move them to proper class
 */
class BatteryService {

    void checkBattery(Battery battery) {
        if (battery.isLowPower()) {
            sendToCharge(battery);
        }
        // other logic
    }

    boolean canLendBattery(Battery battery) {
        return battery.isChargedEnough();
    }

    private void sendToCharge(Battery battery) {
        // logic
    }

}
