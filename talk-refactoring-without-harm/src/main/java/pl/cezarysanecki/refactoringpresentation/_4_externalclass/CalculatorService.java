package pl.cezarysanecki.refactoringpresentation._4_externalclass;

/**
 * Problems:
 *  - impossible to unit test
 * <p>
 * Solution - hide external class behind our interface
 */
class CalculatorService {

    String call(UnitObject unitObject) {
        // a lot of logic
        return prepareResult(unitObject.returnSomeValue());
    }

    String call(ExternalLibraryObject externalLibraryObject) {
        // a lot of logic
        return prepareResult(externalLibraryObject.returnSomeValue());
    }

    private String prepareResult(String value) {
        return value + "-001";
    }

}
