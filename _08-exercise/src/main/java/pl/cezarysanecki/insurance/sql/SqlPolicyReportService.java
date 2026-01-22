package pl.cezarysanecki.insurance.sql;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.cezarysanecki.insurance.PolicyReportDTO;

import java.time.LocalDate;
import java.util.List;

@Service
public class SqlPolicyReportService {

    private final JdbcTemplate jdbcTemplate;

    public SqlPolicyReportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PolicyReportDTO> generateReport(LocalDate fromDate) {
        String sql = """
                SELECT
                    p.id AS policy_id,
                    p.policy_number,
                    p.holder_name,
                    p.start_date,
                    p.end_date,
                    p.status,
                    p.total_premium,
                    COUNT(c.id) AS coverage_count,
                    COALESCE(SUM(c.sum_insured), 0) AS total_sum_insured,
                    STRING_AGG(c.coverage_type, ',') AS coverage_types
                FROM policies p
                LEFT JOIN coverages c ON p.id = c.policy_id
                WHERE p.start_date > ?
                  AND p.status = 'ACTIVE'
                GROUP BY p.id, p.policy_number, p.holder_name,
                         p.start_date, p.end_date, p.status, p.total_premium
                ORDER BY p.start_date DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new PolicyReportDTO(
                rs.getLong("policy_id"),
                rs.getString("policy_number"),
                rs.getString("holder_name"),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getString("status"),
                rs.getBigDecimal("total_premium"),
                rs.getInt("coverage_count"),
                rs.getBigDecimal("total_sum_insured"),
                rs.getString("coverage_types")
        ), fromDate);
    }
}
