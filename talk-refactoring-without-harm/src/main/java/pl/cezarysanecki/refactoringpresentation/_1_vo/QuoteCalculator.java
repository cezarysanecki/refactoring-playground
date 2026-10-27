package pl.cezarysanecki.refactoringpresentation._1_vo;

import lombok.RequiredArgsConstructor;

/**
 * Problems:
 *  - duplicated validation
 *  - potential mismatch of parameters (primitive obsession)
 * <p>
 * Solution - introduce value objects
 */
@RequiredArgsConstructor
class QuoteCalculator {

    private final ExternalServiceQuoteCalculator externalServiceQuoteCalculator;

    double calculateQuoteFor(VinNumber vinNumber, RegistrationNumber registrationNumber) {
        return externalServiceQuoteCalculator.calculate(vinNumber.asString(), registrationNumber.asString());
    }

    double calculateQuoteFor(String vinNumberValue, String registrationNumberValue) {
        VinNumber vinNumber = new VinNumber(vinNumberValue);
        RegistrationNumber registrationNumber = new RegistrationNumber(registrationNumberValue);

        return calculateQuoteFor(vinNumber, registrationNumber);
    }

    private class ExternalServiceQuoteCalculator {

        double calculate(VinNumber vinNumber, RegistrationNumber registrationNumber) {
            // call external API
            return 0.0;
        }

        double calculate(String vinNumber, String registrationNumber) {
            return calculate(new VinNumber(vinNumber), new RegistrationNumber(registrationNumber));
        }

    }
}
