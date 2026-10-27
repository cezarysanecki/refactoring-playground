package pl.cezarysanecki.legacyworkhop;

public interface PolicyRepository {

    Policy getOne(Long id);

    Policy save(Policy policy);
}
