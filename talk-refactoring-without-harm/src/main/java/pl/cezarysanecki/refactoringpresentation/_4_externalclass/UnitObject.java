package pl.cezarysanecki.refactoringpresentation._4_externalclass;

import lombok.RequiredArgsConstructor;

interface UnitObject {

    String returnSomeValue();

    @RequiredArgsConstructor
    class External implements UnitObject {

        private final ExternalLibraryObject externalLibraryObject;

        @Override
        public String returnSomeValue() {
            return externalLibraryObject.returnSomeValue();
        }

    }

}
