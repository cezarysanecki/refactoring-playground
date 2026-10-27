package pl.cezarysanecki.refactoringpresentation._5_splitentities;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

interface VehicleRepository extends CrudRepository<Vehicle, Long> {

    Set<Vehicle> findAllByPolicyId(Long policyId);

    default Vehicles findVehiclesBy(Long policyId) {
        return new Vehicles(findAllByPolicyId(policyId));
    }

}
