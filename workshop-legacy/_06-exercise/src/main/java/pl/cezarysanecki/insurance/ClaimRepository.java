package pl.cezarysanecki.insurance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends CrudRepository<Claim, Long> {

    Claim findByClaimNumber(String claimNumber);

    List<Claim> findAllByPolicyNumber(String policyNumber);
}
