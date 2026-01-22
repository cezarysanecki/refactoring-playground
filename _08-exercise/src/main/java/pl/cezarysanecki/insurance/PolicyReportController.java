package pl.cezarysanecki.insurance;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PolicyReportController {

    private final PolicyRepository policyRepository;

    public PolicyReportController(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @GetMapping("/api/policies/report")
    public List<PolicyReportDTO> generateReport(
            @RequestParam(defaultValue = "2024-01-01") String fromDate) {

        LocalDate from = LocalDate.parse(fromDate);

        List<Policy> policies = policyRepository
                .findAllByStartDateAfterAndStatus(from, PolicyStatus.ACTIVE);

        List<PolicyReportDTO> report = new ArrayList<>();

        for (Policy policy : policies) {
            PolicyReportDTO dto = new PolicyReportDTO();
            dto.setPolicyId(policy.getId());
            dto.setPolicyNumber(policy.getPolicyNumber());
            dto.setHolderName(policy.getHolderName());
            dto.setStartDate(policy.getStartDate());
            dto.setEndDate(policy.getEndDate());
            dto.setStatus(policy.getStatus().name());
            dto.setTotalPremium(policy.getTotalPremium());

            List<String> coverageTypes = policy.getCoverages().stream()
                    .map(c -> c.getCoverageType().name())
                    .toList();

            BigDecimal totalSumInsured = policy.getCoverages().stream()
                    .map(Coverage::getSumInsured)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            dto.setCoverageTypes(coverageTypes);
            dto.setCoverageCount(policy.getCoverages().size());
            dto.setTotalSumInsured(totalSumInsured);

            report.add(dto);
        }

        return report;
    }
}
