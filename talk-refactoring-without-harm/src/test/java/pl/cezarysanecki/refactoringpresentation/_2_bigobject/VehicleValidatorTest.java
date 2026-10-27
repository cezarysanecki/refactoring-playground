package pl.cezarysanecki.refactoringpresentation._2_bigobject;

import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VehicleValidatorTest {

    @Test
    void any_vehicle_cannot_be_too_old() {
        assertThrows(IllegalStateException.class, () -> VehicleValidator.validateIfVehiclesAreNotTooOld(
                Set.of(Year.of(1999), Year.of(2001))));
    }

}
