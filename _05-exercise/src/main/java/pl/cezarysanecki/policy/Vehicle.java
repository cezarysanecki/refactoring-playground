package pl.cezarysanecki.policy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.Year;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Policy policy;

    private Year productionYear;

    // many other fields

    public Long getId() {
        return id;
    }

    public Policy getPolicy() {
        return policy;
    }

    public Year getProductionYear() {
        return productionYear;
    }
}
