package pl.cezarysanecki.refactoringpresentation._5_splitentities;

import org.springframework.data.repository.CrudRepository;

interface PolicyRepository extends CrudRepository<Policy, Long> {
}
