package pl.cezarysanecki.refactoringpresentation._2_bigobject;

import lombok.Value;

import java.util.Set;

@Value
class Policy {

    String value1;
    String value2;
    String value3;
    String value4;
    String value5;
    String value6;
    String value7;
    String value8;
    String value9;
    String value10;
    // many other fields
    Set<Vehicle> vehicles;

}
