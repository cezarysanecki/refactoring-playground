package pl.cezarysanecki.insurance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PolicyReportDTO {

    private Long policyId;
    private String policyNumber;
    private String holderName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private BigDecimal totalPremium;
    private List<String> coverageTypes;
    private int coverageCount;
    private BigDecimal totalSumInsured;

    public PolicyReportDTO() {
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(BigDecimal totalPremium) {
        this.totalPremium = totalPremium;
    }

    public List<String> getCoverageTypes() {
        return coverageTypes;
    }

    public void setCoverageTypes(List<String> coverageTypes) {
        this.coverageTypes = coverageTypes;
    }

    public int getCoverageCount() {
        return coverageCount;
    }

    public void setCoverageCount(int coverageCount) {
        this.coverageCount = coverageCount;
    }

    public BigDecimal getTotalSumInsured() {
        return totalSumInsured;
    }

    public void setTotalSumInsured(BigDecimal totalSumInsured) {
        this.totalSumInsured = totalSumInsured;
    }
}
