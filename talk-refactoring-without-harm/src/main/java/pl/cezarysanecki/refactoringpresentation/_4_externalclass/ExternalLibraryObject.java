package pl.cezarysanecki.refactoringpresentation._4_externalclass;

class ExternalLibraryObject {

    public ExternalLibraryObject() {
        // creating connection to database or external service
        throw new UnsupportedOperationException("timeout");
    }

    String returnSomeValue() {
        return "132";
    }

}
