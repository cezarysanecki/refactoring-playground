package pl.cezarysanecki.refactoringpresentation._1_vo;

public class RegistrationNumber {

    private final String value;

    public RegistrationNumber(String value) {
        this.value = value;
    }

    public String asString() {
        return value;
    }

}
