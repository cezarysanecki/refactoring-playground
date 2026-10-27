package pl.cezarysanecki.refactoringpresentation._6_complexlogic;

/**
 * Problems:
 *  - complex logic
 *  - cannot spot any patterns
 * <p>
 * Solution - inline methods
 */
class TooManyMethods {

    public static void main(String[] args) {
        new TooManyMethods().call();
    }

    void call() {
        System.out.println("Minimize cognitive load for others");
    }

}
