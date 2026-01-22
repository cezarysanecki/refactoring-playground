package pl.cezarysanecki.insurance;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "policy_number", nullable = false, unique = true)
    private String policyNumber;

    @Column(name = "holder_name", nullable = false)
    private String holderName;

    @Column(name = "holder_pesel", nullable = false)
    private String holderPesel;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PolicyStatus status;

    @Column(name = "total_premium", nullable = false)
    private BigDecimal totalPremium;

    @OneToMany(mappedBy = "policy", fetch = FetchType.LAZY)
    private Set<Coverage> coverages = new HashSet<>();

    protected Policy() {
    }

    public Policy(String policyNumber, String holderName, String holderPesel,
                  LocalDate startDate, LocalDate endDate, BigDecimal totalPremium) {
        this.policyNumber = policyNumber;
        this.holderName = holderName;
        this.holderPesel = holderPesel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPremium = totalPremium;
        this.status = PolicyStatus.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getHolderPesel() {
        return holderPesel;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public PolicyStatus getStatus() {
        return status;
    }

    public BigDecimal getTotalPremium() {
        return totalPremium;
    }

    public Set<Coverage> getCoverages() {
        return coverages;
    }

    public void addCoverage(Coverage coverage) {
        coverages.add(coverage);
    }
}
