package pl.cezarysanecki.insurance;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.cezarysanecki.insurance.sql.SqlPolicyReportService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PolicyReportController {

    private final SqlPolicyReportService sqlPolicyReportService;

    public PolicyReportController(SqlPolicyReportService sqlPolicyReportService) {
        this.sqlPolicyReportService = sqlPolicyReportService;
    }

    @GetMapping("/api/policies/report")
    public List<PolicyReportDTO> generateReport(
            @RequestParam(defaultValue = "2024-01-01") String fromDate) {

        LocalDate from = LocalDate.parse(fromDate);

        return sqlPolicyReportService.generateReport(from);
    }
}
