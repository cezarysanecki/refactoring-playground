package pl.cezarysanecki.insurance;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "coverages")
public class Coverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "coverage_type", nullable = false)
    private CoverageType coverageType;

    @Column(name = "sum_insured", nullable = false)
    private BigDecimal sumInsured;

    @Column(name = "premium", nullable = false)
    private BigDecimal premium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;

    protected Coverage() {
    }

    public Coverage(CoverageType coverageType, BigDecimal sumInsured,
                    BigDecimal premium, Policy policy) {
        this.coverageType = coverageType;
        this.sumInsured = sumInsured;
        this.premium = premium;
        this.policy = policy;
    }

    public Long getId() {
        return id;
    }

    public CoverageType getCoverageType() {
        return coverageType;
    }

    public BigDecimal getSumInsured() {
        return sumInsured;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public Policy getPolicy() {
        return policy;
    }
}
