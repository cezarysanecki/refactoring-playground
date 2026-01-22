package pl.cezarysanecki.insurance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PolicyReportNPlusOneTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private CoverageRepository coverageRepository;

    @BeforeEach
    void setUp() {
        coverageRepository.deleteAll();
        policyRepository.deleteAll();
    }

    @Test
    void shouldReturnCorrectReportData() throws Exception {
        // given
        Policy policy1 = createPolicy(
                "POL-001", "Jan Kowalski", "12345678901", new BigDecimal("1500.00")
        );
        createCoverage(CoverageType.OC, new BigDecimal("100000.00"), new BigDecimal("500.00"), policy1);
        createCoverage(CoverageType.AC, new BigDecimal("50000.00"), new BigDecimal("1000.00"), policy1);

        Policy policy2 = createPolicy(
                "POL-002", "Anna Nowak", "98765432109", new BigDecimal("800.00")
        );
        createCoverage(CoverageType.NNW, new BigDecimal("30000.00"), new BigDecimal("200.00"), policy2);

        // when & then
        mockMvc.perform(get("/api/policies/report")
                        .param("fromDate", "2024-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].policyNumber").value("POL-001"))
                .andExpect(jsonPath("$[0].holderName").value("Jan Kowalski"))
                .andExpect(jsonPath("$[0].coverageCount").value(2))
                .andExpect(jsonPath("$[0].totalSumInsured").value(150000.00))
                .andExpect(jsonPath("$[1].policyNumber").value("POL-002"))
                .andExpect(jsonPath("$[1].holderName").value("Anna Nowak"))
                .andExpect(jsonPath("$[1].coverageCount").value(1))
                .andExpect(jsonPath("$[1].totalSumInsured").value(30000.00));
    }

    private Policy createPolicy(String policyNumber, String holderName, String pesel, BigDecimal premium) {
        Policy policy = new Policy(
                policyNumber,
                holderName,
                pesel,
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2025, 6, 1),
                premium
        );
        return policyRepository.save(policy);
    }

    private Coverage createCoverage(CoverageType type, BigDecimal sumInsured, BigDecimal premium, Policy policy) {
        Coverage coverage = new Coverage(type, sumInsured, premium, policy);
        return coverageRepository.save(coverage);
    }
}
