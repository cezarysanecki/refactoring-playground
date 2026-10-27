package pl.cezarysanecki.refactoringpresentation._5_splitentities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String value5;
    // many other fields

}
