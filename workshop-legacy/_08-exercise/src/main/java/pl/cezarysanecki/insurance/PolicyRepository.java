package pl.cezarysanecki.insurance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    List<Policy> findAllByStartDateAfterAndStatus(LocalDate date, PolicyStatus status);
}
