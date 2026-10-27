package pl.cezarysanecki.refactoringpresentation._1_vo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VinNumberTest {

    @Test
    void cannot_create_null_vin_number() {
        assertThrows(IllegalArgumentException.class, () -> new VinNumber(null));
    }

    @ParameterizedTest
    @CsvSource({"0123456789012345", "012345678901234567"})
    void cannot_create_vin_number_with_wrong_length(String value) {
        assertThrows(IllegalArgumentException.class, () -> new VinNumber(value));
    }

}
