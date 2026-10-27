package pl.cezarysanecki.refactoringpresentation._1_vo;

public class VinNumber {

    private final String value;

    public VinNumber(String value) {
        if (value == null) {
            throw new IllegalArgumentException("vin number cannot be null");
        }
        if (value.trim().length() != 17) {
            throw new IllegalArgumentException("vin number should contains 17 letters");
        }
        this.value = value;
    }

    public String asString() {
        return value;
    }

}
