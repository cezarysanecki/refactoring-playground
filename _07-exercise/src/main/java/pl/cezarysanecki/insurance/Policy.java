package pl.cezarysanecki.insurance;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String policyNumber;

    @Enumerated(EnumType.STRING)
    private PolicyType type;

    private BigDecimal insuredAmount;

    public Policy() {
    }

    public Policy(String policyNumber, PolicyType type, BigDecimal insuredAmount) {
        this.policyNumber = policyNumber;
        this.type = type;
        this.insuredAmount = insuredAmount;
    }

    public Long getId() {
        return id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public PolicyType getType() {
        return type;
    }

    public BigDecimal getInsuredAmount() {
        return insuredAmount;
    }
}
